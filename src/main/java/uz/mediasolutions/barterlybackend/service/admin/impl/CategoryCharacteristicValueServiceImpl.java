package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristicValue;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryCharacteristicValueMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryCharacteristicValueDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryCharacteristicValueDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicValueReqDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicRepository;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicValueRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryCharacteristicValueService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class CategoryCharacteristicValueServiceImpl implements CategoryCharacteristicValueService {

    private final CategoryCharacteristicValueRepository categoryCharacteristicValueRepository;
    private final CategoryCharacteristicValueMapper categoryCharacteristicValueMapper;
    private final CategoryCharacteristicRepository categoryCharacteristicRepository;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, Long categoryCharacteristicId, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryCharacteristicValueDTO> valueDTOS = categoryCharacteristicValueRepository.findAll(lang, categoryCharacteristicId, search, pageable);
        return ResponseEntity.ok(valueDTOS);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        CategoryCharacteristicValueDTO2 categoryCharacteristicValue = categoryCharacteristicValueRepository.findByIdCustom(lang, id).orElseThrow(
                () -> RestException.restThrow("Category characteristic value not found", HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(categoryCharacteristicValue);
    }

    @Override
    public ResponseEntity<?> add(CategoryCharacteristicValueReqDTO dto) {
        CategoryCharacteristicValue entity = categoryCharacteristicValueMapper.toEntity(dto);
        categoryCharacteristicValueRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }

    @Override
    public ResponseEntity<?> edit(Long id, CategoryCharacteristicValueReqDTO dto) {
        CategoryCharacteristicValue entity = categoryCharacteristicValueRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category characteristic value not found", HttpStatus.NOT_FOUND)
        );

        CategoryCharacteristic categoryCharacteristic = categoryCharacteristicRepository.findById(dto.getCategoryCharacteristicId()).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.NOT_FOUND)
        );

        entity.setCharacteristic(categoryCharacteristic);
        entity.setTranslations(dto.getTranslations());
        categoryCharacteristicValueRepository.save(entity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        categoryCharacteristicValueRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category characteristic value not found", HttpStatus.NOT_FOUND)
        );
        try {
            categoryCharacteristicValueRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }
}
