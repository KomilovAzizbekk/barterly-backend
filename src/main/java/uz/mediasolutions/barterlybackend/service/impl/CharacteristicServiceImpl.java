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
import uz.mediasolutions.barterlybackend.mapper.CharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.request.CharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;
import uz.mediasolutions.barterlybackend.repository.*;
import uz.mediasolutions.barterlybackend.service.abs.CharacteristicService;

@Service
@AllArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final CategoryRepository categoryRepository;
    private final CharacteristicMapper characteristicMapper;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CharacteristicDTO> characteristics = characteristicRepository.findAllByOrderByCreatedAtDesc(language, pageable);
        return ResponseEntity.ok(characteristics);
    }

    @Override
    public ResponseEntity<CharacteristicResDTO> getById(Long id) {
        Characteristic characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.BAD_REQUEST)
        );
        return ResponseEntity.ok(characteristicMapper.toResDTO(characteristic));
    }

    @Override
    public ResponseEntity<?> add(CharacteristicReqDTO dto) {
        try {
            Characteristic entity = characteristicMapper.toEntity(dto);
            characteristicRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Error with adding", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> edit(Long id, CharacteristicReqDTO dto) {
        Characteristic characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Characteristic not found", HttpStatus.BAD_REQUEST)
        );

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        );

        try {
          characteristic.setTranslations(dto.getTranslations());
          characteristic.setRequired(dto.isRequired());
          characteristic.setCategory(category);
          characteristicRepository.save(characteristic);
          return ResponseEntity.status(HttpStatus.ACCEPTED).body("Edited successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Error with editing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            characteristicRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Error with deleting", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
