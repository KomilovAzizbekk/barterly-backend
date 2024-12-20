package uz.mediasolutions.barterlybackend.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mediasolutions.barterlybackend.entity.*;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ItemDTO;
import uz.mediasolutions.barterlybackend.payload.request.*;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO2;
import uz.mediasolutions.barterlybackend.payload.response.ItemResDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.user.abs.ItemService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemCharacteristicRepository itemCharacteristicRepository;
    private final CategoryRepository categoryRepository;
    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicValueRepository characteristicValueRepository;
    private final ItemImageRepository itemImageRepository;
    private final CharacteristicTypeRepository characteristicTypeRepository;

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
        itemCharacteristicRepository.saveAll(itemCharacteristics);
        itemImageRepository.saveAll(itemImages);

        return Rest.CREATED;
    }

    @Override
    public Page<ItemDTO> getItems(String lang, int page, int size, Boolean premium) {
        // Premium Boolean null bo'lsa barcha itemlarni qaytaramiz, agar true yoki false bolsa turga ajratgan holda
        return premium == null ? itemRepository.findAllForHomeForAllUsers(lang, PageRequest.of(page, size)) :
                itemRepository.findAllForHomeForAllUsers1(lang, premium, PageRequest.of(page, size));
    }


    @Override
    public ItemResDTO getById(String lang, UUID itemId) {
        // Item'ni bazadan ID bo'yicha izlaymiz, agar topilmasa 404 qaytaramiz
        Item item = itemRepository.findByIdAndActiveIsTrue(itemId).orElseThrow(
                () -> RestException.restThrow("Active Item not found", HttpStatus.NOT_FOUND)
        );

        // Image URL'larni olish
        List<String> imageUrlList = item.getItemImages().stream()
                .map(ItemImage::getUrl)
                .collect(Collectors.toList());

        // ItemCharacteristic'larni characteristicTypeId bo‘yicha guruhlab olish
        Map<Long, List<ItemCharacteristic>> characteristicsByType = item.getItemCharacteristics().stream()
                .collect(Collectors.groupingBy(
                        itemCharacteristic -> itemCharacteristic.getCharacteristic().getCharacteristicType().getId()
                ));

        // Barcha characteristicTypes va har birini tegishli characteristic'lari bilan Response DTO yaratamiz
        List<CharacteristicTypeResDTO2> characteristicTypeResDTO2List = characteristicsByType.entrySet().stream()
                .map(entry -> {
                    Long characteristicTypeId = entry.getKey();
                    List<CharacteristicResDTO2> characteristics = entry.getValue().stream()
                            .map(itemCharacteristic -> CharacteristicResDTO2.builder()
                                    .id(itemCharacteristic.getId())
                                    .name(itemCharacteristic.getCharacteristic().getTranslations().get(lang))
                                    .value(itemCharacteristic.getValue() != null ?
                                            itemCharacteristic.getValue().getTranslations().get(lang) : itemCharacteristic.getTextValue())
                                    .build())
                            .collect(Collectors.toList());

                    String characteristicTypeName = characteristicTypeRepository.findById(characteristicTypeId)
                            .map(type -> type.getTranslations().get(lang))
                            .orElse("Unknown");

                    return CharacteristicTypeResDTO2.builder()
                            .id(characteristicTypeId)
                            .name(characteristicTypeName)
                            .characteristics(characteristics)
                            .build();
                })
                .collect(Collectors.toList());

        // Barcha ma'lumotlarni jamlab Response DTO yaratamiz
        return ItemResDTO.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .title(item.getTitle().get(lang))
                .description(item.getDescription())
                .imageUrls(imageUrlList)
                .characteristicTypes(characteristicTypeResDTO2List)
                .build();
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

        // ItemCharacteristic null bo'lmasa bazgaahrirlash kiritamiz
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

        // Agar image urls null bo'lmasa bazaga tahrirlash kiritamiz
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

    @Override
    public Page<ItemDTO> getItemsByUserId(String lang, UUID userId, int page, int size) {
        return itemRepository.finsAllByUserId(userId, lang, PageRequest.of(page, size));
    }

    @Override
    public Page<ItemDTO> getMyItems(String lang, int page, int size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return itemRepository.finsAllByUserId(user.getId(), lang, PageRequest.of(page, size));
    }
}
