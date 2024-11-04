package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Category;
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
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CharacteristicDTO> characteristics = characteristicRepository.findAllByOrderByCreatedAtDesc(lang, search, categoryId, pageable);
        return ResponseEntity.ok(characteristics);
    }


    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        // ID orqali bazadan Characteristic'ni izlaymiz, agar topilmasa 404 qaytaramiz
        Characteristic characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
        );

        // Characteristic'ga bog;langan Categoryni olamiz
        Category category = characteristic.getCategory();

        // Response DTO yaratamiz va 200 status bilan qaytaramiz
        CharacteristicResDTO dto = characteristicMapper.toResDTO(characteristic, category, lang);

        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<?> add(CharacteristicReqDTO dto) {
        Characteristic entity = characteristicMapper.toEntity(dto);
        characteristicRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicReqDTO dto) {
        Characteristic characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND)
        );

        Optional.ofNullable(dto.getTranslations()).ifPresent(characteristic::setTranslations);
        Optional.ofNullable(dto.getRequired()).ifPresent(characteristic::setRequired);
        Optional.ofNullable(dto.getFilter()).ifPresent(characteristic::setFilter);
        Optional.ofNullable(dto.getTitle()).ifPresent(characteristic::setTitle);
        Optional.ofNullable(dto.getCategoryId()).ifPresent(categoryId -> characteristic.setCategory(
                categoryRepository.findById(categoryId).orElseThrow(
                        () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
                )
        ));
        characteristicRepository.save(characteristic);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    public ResponseEntity<?> delete(Long id) {
        if (!characteristicRepository.existsById(id)) {
            throw RestException.restThrow("Characteristic not found", HttpStatus.NOT_FOUND);
        }

        try {
            characteristicRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
