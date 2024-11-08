package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.admin.abs.CharacteristicService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final CategoryRepository categoryRepository;
    private final CharacteristicMapper characteristicMapper;

    private static final Logger log = LoggerFactory.getLogger(CharacteristicServiceImpl.class);

    @Override
    public Page<CharacteristicDTO> getAll(String lang, String search, Long categoryId,
                                          Long characteristicTypeId, int page, int size) {
        return characteristicRepository.findAllByOrderByUpdatedAtDesc(lang, search, categoryId,
                characteristicTypeId, PageRequest.of(page, size));
    }


    @Override
    public CharacteristicResDTO getById(String lang, Long id) {
        // ID orqali bazadan Characteristic'ni izlaymiz, agar topilmasa 404 qaytaramiz
        Characteristic characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
        );

        // Response DTO yaratamiz va qaytaramiz
        return characteristicMapper.toResDTO(characteristic, lang);
    }


    @Override
    public String add(CharacteristicReqDTO dto) {
        characteristicRepository.save(characteristicMapper.toEntity(dto));
        return Rest.CREATED;
    }


    @Override
    public String edit(Long id, CharacteristicReqDTO dto) {
        // ID orqali bazadan Characteristic'ni izlaymiz, agar topilmasa 404 qaytaramiz
        Characteristic characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
        );

        // Null bo'lmagan qiymatlarni Entity'ga set qilamiz
        Optional.ofNullable(dto.getTranslations()).ifPresent(characteristic::setTranslations);
        Optional.ofNullable(dto.getFilter()).ifPresent(characteristic::setFilter);
        Optional.ofNullable(dto.getTitle()).ifPresent(characteristic::setTitle);
        Optional.ofNullable(dto.getCategoryId()).ifPresent(categoryId -> characteristic.setCategory(
                categoryRepository.findById(categoryId).orElseThrow(
                        () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
                )
        ));
        characteristicRepository.save(characteristic);
        return Rest.EDITED;
    }


    @Override
    public String delete(Long id) {
        // ID orqali bazadan Characteristic'ni izlaymiz, agar topilmasa 404 qaytaramiz
        if (!characteristicRepository.existsById(id)) {
            throw RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND);
        }

        try {
            characteristicRepository.deleteById(id);
            return Rest.DELETED;
        } catch (Exception e) {
            log.error("Error while deleting characteristic {}", id, e);
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
