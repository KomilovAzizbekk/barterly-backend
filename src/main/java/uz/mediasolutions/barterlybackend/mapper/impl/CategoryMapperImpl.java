package uz.mediasolutions.barterlybackend.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.mapper.abs.CategoryMapper;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResDTO toResDTO(Category category) {
        if (category == null) {
            return null;
        }
//        JsonNode translationList = category.getTranslations();

        return CategoryResDTO.builder()
                .id(category.getId())
                .translations(category.getTranslations())
                .imageUrl(category.getImageUrl())
                .build();
    }
}
