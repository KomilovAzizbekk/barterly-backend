package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.request.ItemCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.math.BigDecimal;
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


    @Override
    public ResponseEntity<?> add(ItemReqDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        CategoryCharacteristicValue categoryCharacteristicValue = categoryCharacteristicValueRepository.findById(dto.getCategoryCharacteristicValueId()).orElseThrow(
                () -> RestException.restThrow("Category characteristic value not found", HttpStatus.NOT_FOUND)
        );

        User user = (User) CommonUtils.getUserFromSecurityContext();
        Item item = Item.builder()
                .title("ok")
                .description(dto.getDescription())
                .user(user)
                .category(category)
                .swapValue(BigDecimal.valueOf(dto.getValue()))
                .categoryCharacteristicValue(categoryCharacteristicValue)
                .build();

        Item savedItem = itemRepository.saveAndFlush(item);

        List<ItemCharacteristic> itemCharacteristicList = new ArrayList<>();

        for (ItemCharacteristicReqDTO characteristic : dto.getCharacteristics()) {
            Characteristic characteristic1 = characteristicRepository.findById(characteristic.getCharacteristicId()).orElseThrow(
                    () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
            );

            CharacteristicValue characteristicValue = characteristicValueRepository.findById(characteristic.getCharacteristicValueId()).orElseThrow(
                    () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
            );

            ItemCharacteristic itemCharacteristic = ItemCharacteristic.builder()
                    .item(savedItem)
                    .characteristic(characteristic1)
                    .value(characteristicValue)
                    .build();
            itemCharacteristicList.add(itemCharacteristic);
        }
        itemCharacteristicRepository.saveAll(itemCharacteristicList);

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
        return null;
    }
}
