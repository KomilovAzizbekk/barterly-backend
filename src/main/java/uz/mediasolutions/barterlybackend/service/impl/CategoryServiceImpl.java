package uz.mediasolutions.barterlybackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryDTO;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.service.abs.CategoryService;
import uz.mediasolutions.barterlybackend.service.abs.FileService;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileService fileService;

    @Override
    public ResponseEntity<Page<?>> getAllParents(String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryDTO> categoryPage = categoryRepository.findAllByParentCategoryIsNullOrderByCreatedAtDescAndLanguage(language, pageable);
        return ResponseEntity.ok(categoryPage);
    }

    @Override
    public ResponseEntity<Page<?>> getAllByParentId(Long parentId, String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryDTO> categoryPage = categoryRepository.findAllByParentCategoryIdOrderByCreatedAtDescAndLanguage(parentId, language, pageable);
        return ResponseEntity.ok(categoryPage);
    }

    @Override
    public ResponseEntity<CategoryResDTO> getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        );
        CategoryResDTO resDTO = categoryMapper.toResDTO(category);
        return ResponseEntity.ok(resDTO);
    }

    @Override
    public ResponseEntity<?> add(CategoryReqDTO dto) {
        Category entity = categoryMapper.toEntity(dto);
        try {
            categoryRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<?> edit(Long id, CategoryReqDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        );
        try {
            if (dto.getParentCategoryId() != null) {
                category.setParentCategory(categoryRepository.findById(dto.getParentCategoryId()).orElse(null));
            }

            if (!category.getImageUrl().equals(dto.getImageUrl())) {
                //Deleting previous file
                fileService.deleteFile(category.getImageUrl());
            }

            category.setImageUrl(dto.getImageUrl());
            category.setTranslations(dto.getTranslations());
            categoryRepository.save(category);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.BAD_REQUEST)
        );
        try {
            fileService.deleteFile(category.getImageUrl());
            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
