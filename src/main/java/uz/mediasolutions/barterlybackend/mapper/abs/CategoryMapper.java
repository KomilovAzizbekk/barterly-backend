package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO2;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;

public interface CategoryMapper {

    CategoryResDTO toResDTO(Category category);

    Category toEntity(CategoryReqDTO dto);

    CategoryResDTO2 toRes2DTO(Category category, Category parentCategory, String lang);
}
