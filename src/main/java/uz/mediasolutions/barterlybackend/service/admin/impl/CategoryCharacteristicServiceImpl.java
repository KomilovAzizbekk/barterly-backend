package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryCharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryCharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryCharacteristicDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicRepository;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryCharacteristicService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class CategoryCharacteristicServiceImpl implements CategoryCharacteristicService {

    private final CategoryCharacteristicRepository categoryCharacteristicRepository;
    private final CategoryCharacteristicMapper categoryCharacteristicMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long categoryId,
                                          Long parentCharacteristicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryCharacteristicDTO> dtos = categoryCharacteristicRepository.findAllCustom(lang, search, categoryId,
                parentCharacteristicId, pageable);
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        CategoryCharacteristicDTO2 categoryCharacteristic = categoryCharacteristicRepository.findByIdCustom(lang, id).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(categoryCharacteristic);
    }

    @Override
    public ResponseEntity<?> add(CategoryCharacteristicReqDTO dto) {
        CategoryCharacteristic categoryCharacteristic = categoryCharacteristicMapper.toEntity(dto);
        categoryCharacteristicRepository.save(categoryCharacteristic);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(Long id, CategoryCharacteristicReqDTO dto) {
        CategoryCharacteristic categoryCharacteristic = categoryCharacteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.NOT_FOUND)
        );

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        if (dto.getParentId() != null) {
            categoryCharacteristic.setParent(categoryCharacteristicRepository.findById(dto.getParentId()).orElse(null));
        }

        categoryCharacteristic.setCategory(category);
        categoryCharacteristic.setTranslations(dto.getTranslations());
        categoryCharacteristicRepository.save(categoryCharacteristic);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        categoryCharacteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.NOT_FOUND)
        );
        try {
            categoryCharacteristicRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }

}