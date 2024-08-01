package uz.mediasolutions.barterlybackend.service.impl;

import lombok.AllArgsConstructor;
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
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.abs.CharacteristicService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@AllArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final CategoryRepository categoryRepository;
    private final CharacteristicMapper characteristicMapper;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CharacteristicDTO> characteristics = characteristicRepository.findAllByOrderByCreatedAtDesc(lang, search, categoryId, pageable);
        return ResponseEntity.ok(characteristics);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        CharacteristicDTO characteristic = characteristicRepository.findByIdCustom(lang, id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.BAD_REQUEST)
        );
        return ResponseEntity.ok(characteristic);
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
                () -> RestException.restThrow("Characteristic not found", HttpStatus.BAD_REQUEST)
        );

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        );

        characteristic.setTranslations(dto.getTranslations());
        characteristic.setRequired(dto.isRequired());
        characteristic.setCategory(category);
        characteristicRepository.save(characteristic);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            characteristicRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
