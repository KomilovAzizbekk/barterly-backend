package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.CharacteristicType;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicTypeMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicTypeDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicTypeReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO3;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.repository.CharacteristicTypeRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CharacteristicTypeService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacteristicTypeServiceImpl implements CharacteristicTypeService {

    private final CharacteristicTypeRepository characteristicTypeRepository;
    private final CharacteristicTypeMapper characteristicTypeMapper;
    private final CategoryRepository categoryRepository;

    Logger log = LoggerFactory.getLogger(CharacteristicTypeServiceImpl.class);

    @Override
    public Page<CharacteristicTypeDTO> getAll(String lang, String search, Long categoryId, int page, int size) {
        return characteristicTypeRepository.findAllCustom(lang, search, categoryId, PageRequest.of(page, size));
    }

    @Override
    public CharacteristicTypeResDTO getById(String lang, Long id) {
        // ID bo'yicha Characteristic Type ni bazadan izlaymiz, agar topilmasa 404 qaytarib yuboramiz
        CharacteristicType characteristicType = characteristicTypeRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic type not found", HttpStatus.NOT_FOUND)
        );

        return characteristicTypeMapper.toResDTO(characteristicType, lang);
    }

    @Override
    public String add(CharacteristicTypeReqDTO dto) {
        try {
            // Mapper orqali yaratgan Entity'ni bazaga saqlashga harakat qilamiz, agar xatolik yuzaga kelsa catch blokiga otiladi
            characteristicTypeRepository.save(characteristicTypeMapper.toEntity(dto));
            return Rest.CREATED;
        } catch (Exception e) {
            log.error("Error while adding characteristic type: {}", e.getMessage());
            throw RestException.restThrow("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String edit(Long id, CharacteristicTypeReqDTO dto) {
        try {
            // ID bo'yicha Characteristic Type ni bazadan izlaymiz, agar topilmasa 404 qaytarib yuboramiz
            CharacteristicType characteristicType = characteristicTypeRepository.findById(id).orElseThrow(
                    () -> RestException.restThrow("Characteristic type not found", HttpStatus.NOT_FOUND)
            );

            // Null bo'lmagan qiymatlarni Entity'ga set qilib chiqamiz
            Optional.ofNullable(dto.getTranslations()).ifPresent(characteristicType::setTranslations);
            Optional.ofNullable(dto.getCategoryId()).ifPresent(categoryId -> {
                Category category = categoryRepository.findById(categoryId).orElseThrow(
                        () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
                );
                characteristicType.setCategory(category);
            });

            // Bazaga saqlaymiz
            characteristicTypeRepository.save(characteristicType);
            return Rest.EDITED;
        } catch (Exception e) {
            log.error("Error while editing characteristic type: {}", e.getMessage());
            throw RestException.restThrow("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String delete(Long id) {
        if (!characteristicTypeRepository.existsById(id)) {
            throw RestException.restThrow("Characteristic type not found", HttpStatus.NOT_FOUND);
        }
        try {
            characteristicTypeRepository.deleteById(id);
            return Rest.DELETED;
        } catch (Exception e) {
            log.error("Error while deleting characteristic type: {}", e.getMessage());
            throw RestException.restThrow("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CharacteristicTypeResDTO3> getByCategoryId(String lang, Long categoryId) {
        return characteristicTypeRepository.findByCategoryIdCustom(lang, categoryId);
    }
}
