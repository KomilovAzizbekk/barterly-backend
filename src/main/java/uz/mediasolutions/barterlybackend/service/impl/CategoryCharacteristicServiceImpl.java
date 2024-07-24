package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.CategoryCharacteristicMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryCharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.request.CategoryCharacteristicReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryCharacteristicResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryCharacteristicRepository;
import uz.mediasolutions.barterlybackend.service.abs.CategoryCharacteristicService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class CategoryCharacteristicServiceImpl implements CategoryCharacteristicService {

    private final CategoryCharacteristicRepository categoryCharacteristicRepository;
    private final CategoryCharacteristicMapper categoryCharacteristicMapper;

    @Override
    public ResponseEntity<Page<?>> getAll(String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryCharacteristicDTO> dtos = categoryCharacteristicRepository.findByByLang(language, pageable);
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        CategoryCharacteristic categoryCharacteristic = categoryCharacteristicRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category characteristic not found", HttpStatus.BAD_REQUEST)
        );

        CategoryCharacteristicResDTO resDTO = categoryCharacteristicMapper.toResDTO(categoryCharacteristic);
        return ResponseEntity.ok(resDTO);
    }

    @Override
    public ResponseEntity<?> add(CategoryCharacteristicReqDTO dto) {
        try {
            CategoryCharacteristic categoryCharacteristic = categoryCharacteristicMapper.toEntity(dto);
            categoryCharacteristicRepository.save(categoryCharacteristic);
            return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Rest.ERROR);
        }
    }


    @Override
    public ResponseEntity<?> edit(Long id, CategoryCharacteristicReqDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
