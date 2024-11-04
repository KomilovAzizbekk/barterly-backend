package uz.mediasolutions.barterlybackend.mapper.abs;

import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.payload.request.CategoryReqDTO;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;

public interface CategoryMapper {

    Category toEntity(CategoryReqDTO dto);

    CategoryResDTO toResDTO(Category category, Category parentCategory, String lang);
}
