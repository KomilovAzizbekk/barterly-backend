package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.Item2DTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemCharacteristicRepository itemCharacteristicRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryCharacteristicValueRepository categoryCharacteristicValueRepository;
    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicValueRepository characteristicValueRepository;
    private final ItemImageRepository itemImageRepository;
    private final CategoryCharacteristicRepository categoryCharacteristicRepository;
    private final ItemCategoryCharacteristicRepository itemCategoryCharacteristicRepository;


    @Override
    public ResponseEntity<?> add(ItemReqDTO dto) {

        //Checking Category, CategoryCharacteristic, CategoryCharacteristicValue exist in database or else throw! (41-51 lines)
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        CategoryCharacteristicValue categoryCharacteristicValue = categoryCharacteristicValueRepository.findById(dto.getCategoryCharacteristicValueId()).orElseThrow(
                () -> RestException.restThrow("Category characteristic value not found", HttpStatus.NOT_FOUND)
        );

        CategoryCharacteristic categoryCharacteristic = categoryCharacteristicRepository.findById(dto.getCategoryCharacteristicId()).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.NOT_FOUND)
        );

        //Getting User from Security Context
        User user = (User) CommonUtils.getUserFromSecurityContext();

        //Creating item and save + flush it to database (57-63 lines)
        Item item = Item.builder()
                .description(dto.getDescription())
                .user(user)
                .category(category)
                .build();

        Item savedItem = itemRepository.saveAndFlush(item);

        /*Creating itemCategoryCharacteristic and save it to database (68-74 lines)
        This is for connect categoryCharacteristics and its value to item.
        */
        ItemCategoryCharacteristic itemCategoryCharacteristic = ItemCategoryCharacteristic.builder()
                .categoryCharacteristic(categoryCharacteristic)
                .categoryCharacteristicValue(categoryCharacteristicValue)
                .item(savedItem)
                .build();

        itemCategoryCharacteristicRepository.save(itemCategoryCharacteristic);

        //Iterating characteristics list and save them all to itemCharacteristics table. (77-93 lines)
        List<ItemCharacteristic> itemCharacteristicList = new ArrayList<>();
        for (ItemCharacteristicReqDTO characteristic : dto.getCharacteristics()) {
            Characteristic characteristic1 = characteristicRepository.findById(characteristic.getCharacteristicId()).orElseThrow(
                    () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
            );

            CharacteristicValue characteristicValue = characteristic.getCharacteristicValueId() != null ? characteristicValueRepository.findById(characteristic.getCharacteristicValueId()).orElse(null) : null;

            ItemCharacteristic itemCharacteristic = ItemCharacteristic.builder()
                    .item(savedItem)
                    .characteristic(characteristic1)
                    .value(characteristicValue)
                    .textValue(characteristic.getValue() != null ? characteristic.getValue() : "")
                    .build();
            itemCharacteristicList.add(itemCharacteristic);
        }
        itemCharacteristicRepository.saveAll(itemCharacteristicList);

        //Saving all the image urls to itemImages table. (96-102 lines)
        for (String imageUrl : dto.getImageUrls()) {
            ItemImage itemImage = ItemImage.builder()
                    .item(savedItem)
                    .url(imageUrl)
                    .build();
            itemImageRepository.save(itemImage);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }

    @Override
    public ResponseEntity<?> getById(String lang, UUID itemId) {
        Item2DTO byIdCustom = itemRepository.findByIdCustom(lang, itemId);
        System.out.println(byIdCustom.toString());
        return ResponseEntity.ok(byIdCustom);
    }
}
