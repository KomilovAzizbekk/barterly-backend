package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.Item2DTO;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO2;
import uz.mediasolutions.barterlybackend.payload.request.ItemCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.sql.Timestamp;
import java.util.*;

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

    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> add(ItemReqDTO dto) {

        Map<String, String> title = new HashMap<>();
        StringBuilder uz = new StringBuilder();
        StringBuilder ru = new StringBuilder();
        StringBuilder en = new StringBuilder();

        //Checking Category, CategoryCharacteristic, CategoryCharacteristicValue exist in database or else throw!
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        //Getting User from Security Context
        User user = (User) CommonUtils.getUserFromSecurityContext();

        //Creating item and save + flush it to database
        Item item = Item.builder()
                .description(dto.getDescription())
                .user(user)
                .category(category)
                .active(dto.getIsActive())
                .premium(dto.getIsPremium())
                .temporary(dto.getIsTemporary())
                .temporaryToDate(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
                .build();

        Item savedItem = itemRepository.saveAndFlush(item);

        List<CategoryCharacteristicReqDTO2> categoryCharacteristics = dto.getCategoryCharacteristics();

        for (int i = 0; i < categoryCharacteristics.size(); i++) {
            CategoryCharacteristicValue categoryCharacteristicValue = categoryCharacteristicValueRepository.findById(categoryCharacteristics.get(i).getCategoryCharacteristicValueId()).orElseThrow(
                    () -> RestException.restThrow("Category characteristic value not found", HttpStatus.NOT_FOUND)
            );

            CategoryCharacteristic categoryCharacteristic = categoryCharacteristicRepository.findById(categoryCharacteristics.get(i).getCategoryCharacteristicId()).orElseThrow(
                    () -> RestException.restThrow("Category characteristic not found", HttpStatus.NOT_FOUND)
            );

            //If categoryCharacteristic's value should be in title -> We are generating title for it.
            if (categoryCharacteristic.isTitle()) {
                Map<String, String> translations = categoryCharacteristicValue.getTranslations();
                uz.append(translations.getOrDefault("uz", "")).append(" ");
                ru.append(translations.getOrDefault("ru", "")).append(" ");
                en.append(translations.getOrDefault("en", "")).append(" ");
            }

            if (i == categoryCharacteristics.size() - 1) {
                 /*Creating itemCategoryCharacteristic and save it to database
                 This is for connect categoryCharacteristics and its value to item.
                 */
                ItemCategoryCharacteristic itemCategoryCharacteristic = ItemCategoryCharacteristic.builder()
                        .categoryCharacteristic(categoryCharacteristic)
                        .title(categoryCharacteristic.isTitle())
                        .categoryCharacteristicValue(categoryCharacteristicValue)
                        .item(savedItem)
                        .build();

                itemCategoryCharacteristicRepository.save(itemCategoryCharacteristic);
            }
        }

        //Iterating characteristics list and save them all to itemCharacteristics table.
        List<ItemCharacteristic> itemCharacteristicList = new ArrayList<>();
        for (ItemCharacteristicReqDTO characteristic : dto.getCharacteristics()) {
            Characteristic characteristic1 = characteristicRepository.findById(characteristic.getCharacteristicId()).orElseThrow(
                    () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
            );

            //If characteristic value should display in title -> generating title
            if (characteristic1.isTitle() && characteristic.getCharacteristicValueId() != null) {
                CharacteristicValue characteristicValue = characteristicValueRepository.findById(characteristic.getCharacteristicValueId()).orElseThrow(
                        () -> RestException.restThrow("Characteristic value not found", HttpStatus.NOT_FOUND)
                );
                Map<String, String> translations = characteristicValue.getTranslations();
                uz.append(", ").append(translations.getOrDefault("uz", ""));
                ru.append(", ").append(translations.getOrDefault("ru", ""));
                en.append(", ").append(translations.getOrDefault("en", ""));
            }

            CharacteristicValue characteristicValue = characteristic.getCharacteristicValueId() != null ? characteristicValueRepository.findById(characteristic.getCharacteristicValueId()).orElse(null) : null;

            ItemCharacteristic itemCharacteristic = ItemCharacteristic.builder()
                    .item(savedItem)
                    .title(characteristic1.isTitle())
                    .characteristic(characteristic1)
                    .value(characteristicValue)
                    .textValue(characteristic.getValue() != null ? characteristic.getValue() : "")
                    .build();
            itemCharacteristicList.add(itemCharacteristic);
        }
        itemCharacteristicRepository.saveAll(itemCharacteristicList);

        title.put("uz", uz.toString());
        title.put("ru", ru.toString());
        title.put("en", en.toString());

        savedItem.setTitle(title);
        itemRepository.save(savedItem);

        //Saving all the image urls to itemImages table.
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
    public ResponseEntity<?> getById(String lang, UUID itemId, boolean active) {
        Item2DTO byIdCustom = itemRepository.findByIdCustom(lang, itemId, active);
        System.out.println(byIdCustom.toString());
        return ResponseEntity.ok(byIdCustom);
    }

    @Override
    public ResponseEntity<?> edit(UUID itemId, ItemEditReqDTO dto) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> RestException.restThrow("Item not found", HttpStatus.NOT_FOUND)
        );
        User user = (User) CommonUtils.getUserFromSecurityContext();
        assert user != null;
        if (!item.getUser().getId().equals(user.getId())) {
            throw RestException.restThrow("You do not have permission to edit this item", HttpStatus.FORBIDDEN);
        }
        if (dto.getCharacteristics() != null) {
            List<ItemCharacteristicReqDTO> characteristics = dto.getCharacteristics();
            for (ItemCharacteristicReqDTO characteristic : characteristics) {
                Optional<ItemCharacteristic> optionalItemCharacteristic = itemCharacteristicRepository.findByItemIdAndCharacteristicId(itemId, characteristic.getCharacteristicId());

                Characteristic characteristic1 = characteristicRepository.findById(characteristic.getCharacteristicId()).orElseThrow(
                        () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
                );

                if (optionalItemCharacteristic.isPresent()) {
                    ItemCharacteristic itemCharacteristic = optionalItemCharacteristic.get();
                    itemCharacteristic.setCharacteristic(characteristic1);
                    itemCharacteristic.setItem(item);
                    itemCharacteristic.setTextValue(characteristic.getValue());
                    itemCharacteristic.setValue(characteristicValueRepository.findById(characteristic.getCharacteristicValueId()).orElse(null));
                    itemCharacteristic.setTitle(characteristic1.isTitle());
                }
            }
        }

        if (dto.getDescription() != null) {

        }

        if (dto.getImageUrls() != null) {

        }

        if (dto.getIsActive() != null) {

        }

        if (dto.getIsTemporary() != null) {

        }

        if (dto.getIsPremium() != null) {

        }
        return null;
    }
}
