package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.Item2DTO;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO2;
import uz.mediasolutions.barterlybackend.payload.request.ItemEditReqDTO;
import uz.mediasolutions.barterlybackend.payload.request.ItemReqDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Transactional
    @Override
    public String add(ItemReqDTO dto) {
        Map<String, String> title = new HashMap<>();
        StringBuilder uz = new StringBuilder();
        StringBuilder ru = new StringBuilder();
        StringBuilder en = new StringBuilder();

        // Category va Userni olish
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND));
        User user = (User) CommonUtils.getUserFromSecurityContext();

        if (user == null) {
            throw RestException.restThrow("User is not authorized", HttpStatus.UNAUTHORIZED);
        }

        // Item obyektini yaratish, lekin darhol saqlamaymiz
        Item item = Item.builder()
                .description(dto.getDescription())
                .user(user)
                .category(category)
                .active(dto.getIsActive())
                .premium(dto.getIsPremium())
                .temporary(dto.getIsTemporary())
                .temporaryToDate(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
                .build();

        // CategoryCharacteristics va CategoryCharacteristicValues ni oldindan olish
        Map<Long, CategoryCharacteristic> characteristicsMap = categoryCharacteristicRepository
                .findAllById(dto.getCategoryCharacteristics().stream()
                        .map(CategoryCharacteristicReqDTO2::getCategoryCharacteristicId)
                        .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(CategoryCharacteristic::getId, Function.identity()));

        Map<Long, CategoryCharacteristicValue> characteristicValuesMap = categoryCharacteristicValueRepository
                .findAllById(dto.getCategoryCharacteristics().stream()
                        .map(CategoryCharacteristicReqDTO2::getCategoryCharacteristicValueId)
                        .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(CategoryCharacteristicValue::getId, Function.identity()));

        // ItemCategoryCharacteristics obyektlarini yaratish
        List<ItemCategoryCharacteristic> itemCategoryCharacteristics = dto.getCategoryCharacteristics().stream()
                .map(categoryCharacteristicReqDTO2 -> {
                    CategoryCharacteristic categoryCharacteristic = characteristicsMap.get(categoryCharacteristicReqDTO2.getCategoryCharacteristicId());
                    CategoryCharacteristicValue categoryCharacteristicValue = characteristicValuesMap.get(categoryCharacteristicReqDTO2.getCategoryCharacteristicValueId());

                    if (categoryCharacteristic.isTitle()) {
                        Map<String, String> translations = categoryCharacteristicValue.getTranslations();
                        uz.append(translations.get("uz")).append(", ");
                        ru.append(translations.get("ru")).append(", ");
                        en.append(translations.get("en")).append(", ");
                    }

                    return ItemCategoryCharacteristic.builder()
                            .title(categoryCharacteristic.isTitle())
                            .categoryCharacteristic(categoryCharacteristic)
                            .categoryCharacteristicValue(categoryCharacteristicValue)
                            .item(item)
                            .build();
                })
                .collect(Collectors.toList());

        // ItemCharacteristics obyektlarini yaratish
        List<ItemCharacteristic> itemCharacteristics = dto.getCharacteristics().stream()
                .map(characteristic -> {
                    Characteristic characteristicEntity = characteristicRepository.findById(characteristic.getCharacteristicId())
                            .orElseThrow(() -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND));

                    CharacteristicValue characteristicValue = characteristic.getCharacteristicValueId() != null ?
                            characteristicValueRepository.findById(characteristic.getCharacteristicValueId()).orElse(null) : null;

                    if (characteristicEntity.isTitle()) {
                        uz.append(characteristicValue != null ? characteristicValue.getTranslations().get("uz") : characteristic.getValue()).append(", ");
                        ru.append(characteristicValue != null ? characteristicValue.getTranslations().get("ru") : characteristic.getValue()).append(", ");
                        en.append(characteristicValue != null ? characteristicValue.getTranslations().get("en") : characteristic.getValue()).append(", ");
                    }

                    return ItemCharacteristic.builder()
                            .item(item)
                            .title(characteristicEntity.isTitle())
                            .characteristic(characteristicEntity)
                            .value(characteristicValue)
                            .textValue(characteristic.getValue() != null ? characteristic.getValue() : "")
                            .build();
                })
                .collect(Collectors.toList());

        // Image URL larni saqlash
        List<ItemImage> itemImages = dto.getImageUrls().stream()
                .map(imageUrl -> ItemImage.builder()
                        .item(item)
                        .url(imageUrl)
                        .build())
                .collect(Collectors.toList());

        // Title ni to'g'rilash (oxirgi vergulni olib tashlash)
        title.put("uz", uz.length() > 2 ? uz.substring(0, uz.length() - 2) : uz.toString());
        title.put("ru", ru.length() > 2 ? ru.substring(0, ru.length() - 2) : ru.toString());
        title.put("en", en.length() > 2 ? en.substring(0, en.length() - 2) : en.toString());
        item.setTitle(title);

        // Barcha bog'liq obyektlarni batch saqlash
        itemRepository.save(item); // Barcha obyektlar bog'langanidan keyin saqlaymiz
        itemCategoryCharacteristicRepository.saveAll(itemCategoryCharacteristics);
        itemCharacteristicRepository.saveAll(itemCharacteristics);
        itemImageRepository.saveAll(itemImages);

        return Rest.CREATED;
    }


    @Override
    public Item2DTO getById(String lang, UUID itemId, boolean active) {
        return itemRepository.findByIdCustom(lang, itemId, active);
    }

    @Override
    public String edit(UUID itemId, ItemEditReqDTO dto) {
        // Bazadan Item'ni ID bo'yicha izlaymiz, agar yo'q bo'lsa 404 qaytaramiz
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> RestException.restThrow("Item not found", HttpStatus.NOT_FOUND)
        );

        // Security contextdan user'ni get qilib olamiz
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Agar user contextda topilmasa 401 qaytarib yuboramiz
        if (user == null) {
            throw RestException.restThrow("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Tahrirlanayotgan Item aynan shu user'ga tegishli bo'lmasa 403 qaytaramiz
        if (!item.getUser().getId().equals(user.getId())) {
            throw RestException.restThrow("You do not have permission to edit this item", HttpStatus.FORBIDDEN);
        }
        // Null bo'lmagan qiymatlarni saqlaymiz
        Optional.ofNullable(dto.getDescription()).ifPresent(item::setDescription);
        Optional.ofNullable(dto.getIsPremium()).ifPresent(item::setPremium);
        Optional.ofNullable(dto.getIsTemporary()).ifPresent(item::setTemporary);
        Optional.ofNullable(dto.getIsActive()).ifPresent(item::setActive);

        itemRepository.save(item);

        // ItemCharacteristic
        if (dto.getCharacteristics() != null) {
            List<ItemCharacteristic> itemCharacteristics = dto.getCharacteristics().stream()
                    .map(itemCharacteristicReqDTO -> {

                        Characteristic characteristic = characteristicRepository.findById(itemCharacteristicReqDTO.getCharacteristicId()).orElseThrow(
                                () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
                        );

                        ItemCharacteristic itemCharacteristic = itemCharacteristicRepository.findById(itemCharacteristicReqDTO.getId()).orElse(null);

                        if (itemCharacteristic == null) {
                            return null;
                        }

                        itemCharacteristic.setCharacteristic(characteristic);
                        itemCharacteristic.setItem(item);
                        itemCharacteristic.setTextValue(itemCharacteristicReqDTO.getValue());
                        itemCharacteristic.setValue(characteristicValueRepository.findById(itemCharacteristicReqDTO.getCharacteristicValueId()).orElse(null));
                        itemCharacteristic.setTitle(characteristic.isTitle());
                        return itemCharacteristic;
                    }).toList();
            itemCharacteristicRepository.saveAll(itemCharacteristics);
        }

        if (dto.getImageUrls() != null) {
            List<ItemImage> itemImages = dto.getImageUrls().stream()
                    .map(imageUrl -> ItemImage.builder()
                            .item(item)
                            .url(imageUrl)
                            .build()).toList();
            itemImageRepository.saveAll(itemImages);
        }

        return Rest.EDITED;
    }
}
