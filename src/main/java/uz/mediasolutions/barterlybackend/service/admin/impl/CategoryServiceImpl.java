package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryMapper;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryDTO2;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryService;
import uz.mediasolutions.barterlybackend.service.common.abs.FileService;
import uz.mediasolutions.barterlybackend.utills.CommonUtils;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileService fileService;

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long parentId, int page, int size) {
        User user = (User) CommonUtils.getUserFromSecurityContext();
        assert user != null;
        System.out.println(user.getUsername() + " : " + user.getAuthorities() + " : " + user.getRole().getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryDTO> categoryPage = categoryRepository.findAllCustom(lang, search, parentId, pageable);
        return ResponseEntity.ok(categoryPage);
    }


    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        CategoryDTO2 categoryDTO2 = categoryRepository.findByIdCustom(lang, id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(categoryDTO2);
    }

    @Override
    public ResponseEntity<?> add(CategoryReqDTO dto) {
        Category entity = categoryMapper.toEntity(dto);
        categoryRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(Long id, CategoryReqDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );
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
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );
        fileService.deleteFile(category.getImageUrl());
        try {
            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
