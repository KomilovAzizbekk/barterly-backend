package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryDTO2;
import uz.mediasolutions.barterlybackend.payload.response.CategoryResDTO;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT c.id,\n" +
            "       c.image_url               as imageUrl,\n" +
            "       c.translations ->> :lang  as name,\n" +
            "       c2.id                     as parentId,\n" +
            "       c2.translations ->> :lang as parentName\n" +
            "FROM categories c\n" +
            "         LEFT JOIN categories c2 on c2.id = c.parent_id\n" +
            "WHERE (:search IS NULL OR c.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:parentId IS NULL OR c.parent_id = :parentId)\n" +
            "  AND c.translations ->> :lang IS NOT NULL\n" +
            "  AND c2.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY c.created_at DESC", nativeQuery = true)
    Page<CategoryDTO> findAllCustom(@Param("lang") String lang,
                                    @Param("search") String search,
                                    @Param("parentId") Long parentId,
                                    Pageable pageable);


    @Query(value = "SELECT c.id,\n" +
            "       c.image_url               as imageUrl,\n" +
            "       c.translations            as names,\n" +
            "       c2.id                     as parentId,\n" +
            "       c2.translations ->> :lang as parentName\n" +
            "FROM categories c\n" +
            "         LEFT JOIN categories c2 on c2.id = c.parent_id\n" +
            "WHERE c2.translations ->> :lang IS NOT NULL\n" +
            "AND c.id=:id", nativeQuery = true)
    Optional<CategoryDTO2> findByIdCustom(@Param("lang") String lang,
                                         @Param("id") Long id);
}
