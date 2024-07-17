package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Category;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryDTO;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT\n" +
            "    id,\n" +
            "    image_url as imageUrl,\n" +
            "    translations ->> :language as name\n" +
            "FROM\n" +
            "    categories\n" +
            "WHERE\n" +
            "    parent_id IS NULL AND\n" +
            "    translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;", nativeQuery = true)
    Page<CategoryDTO> findAllByParentCategoryIsNullOrderByCreatedAtDescAndLanguage(@Param("language") String language,
                                                                                   Pageable pageable);

    @Query(value = "SELECT\n" +
            "    id,\n" +
            "    image_url as imageUrl,\n" +
            "    translations ->> :language as name\n" +
            "FROM\n" +
            "    categories\n" +
            "WHERE\n" +
            "    parent_id = :parent_id AND\n" +
            "    translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;", nativeQuery = true)
    Page<CategoryDTO> findAllByParentCategoryIdOrderByCreatedAtDescAndLanguage(
            @Param("parent_id") Long parentId,
            @Param("language") String language,
            Pageable pageable);
}
