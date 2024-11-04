package uz.mediasolutions.barterlybackend.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryMapper;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;
import uz.mediasolutions.barterlybackend.repository.CategoryRepository;
import uz.mediasolutions.barterlybackend.repository.ItemRepository;
import uz.mediasolutions.barterlybackend.service.admin.abs.CategoryService;
import uz.mediasolutions.barterlybackend.service.common.impl.FileServiceImpl;
import uz.mediasolutions.barterlybackend.utills.constants.Rest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileServiceImpl fileService;
    private final ItemRepository itemRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public ResponseEntity<Page<?>> getAll(String lang, String search, Long parentId, int page, int size) {
        return ResponseEntity.ok(categoryRepository.findAllCustom(lang, search, parentId, PageRequest.of(page, size)));
    }


    @Override
    public ResponseEntity<?> getById(String lang, Long id) {
        // Berilgan ID bo'yicha category'ni izlaymiz agar bo'lmasa 404 qaytaramiz
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category Not Found", HttpStatus.NOT_FOUND)
        );

        // Mavjuda category'ning parent category'sini olamiz
        Category parentCategory = category.getParentCategory();

        // Response DTO yaratib olamiz va 200 status bilan qaytaramiz
        CategoryResDTO dto = categoryMapper.toResDTO(category, parentCategory, lang);

        return ResponseEntity.ok(dto);
    }


    @Override
    public ResponseEntity<?> add(CategoryReqDTO dto) {
        Category entity = categoryMapper.toEntity(dto);
        categoryRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Rest.CREATED);
    }


    @Override
    public ResponseEntity<?> edit(Long id, CategoryReqDTO dto) {
        // Berilgan ID bo'yicha category'ni izlaymiz agar bo'lmasa 404 qaytaramiz
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        // Agar DTO da berilgan imageUrl null bo'lmasa eski rasmni serverdan o'chirib yuboramiz
        if (dto.getImageUrl() != null) {
            fileService.deleteAttachedFile(category.getImageUrl());
        }

        // Null b'lmagan barcha fieldlarni category'ga set qilib save qilamiz
        Optional.ofNullable(dto.getImageUrl()).ifPresent(category::setImageUrl);
        Optional.ofNullable(dto.getTranslations()).ifPresent(category::setTranslations);
        Optional.ofNullable(dto.getParentCategoryId()).ifPresent(parentCategoryId -> category.setParentCategory(
                categoryRepository.findById(parentCategoryId).orElse(null)
        ));
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Rest.EDITED);
    }


    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        // Berilgan ID bo'yicha category'ni izlaymiz agar bo'lmasa 404 qaytaramiz
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Category not found", HttpStatus.NOT_FOUND)
        );

        // Category'ga tegishli rasmni serverdan o'chirib yuboramiz
        fileService.deleteAttachedFile(category.getImageUrl());
        try {
            // Ushbu category'ga tegishli item'larni deleted = true va category_id = null qilib qo'yamiz
            itemRepository.deleteAllByCategoryId(id);

            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Rest.DELETED);
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow(Rest.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
