package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryCharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryCharacteristicDTO2;

import java.util.Optional;

public interface CategoryCharacteristicRepository extends JpaRepository<CategoryCharacteristic, Long> {

    @Query(value = "SELECT cch.id,\n" +
            "       cch.translations ->> :lang  as name,\n" +
            "       c.id                        as categoryId,\n" +
            "       c.translations ->> :lang    as categoryName,\n" +
            "       cch2.id                     as parentCharacteristicId,\n" +
            "       cch2.translations ->> :lang as parentCharacteristicName\n" +
            "FROM category_characteristics cch\n" +
            "         LEFT JOIN categories c on c.id = cch.category_id\n" +
            "         LEFT JOIN category_characteristics cch2 on cch2.id = cch.parent_id\n" +
            "WHERE (:search IS NULL OR cch.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:categoryId IS NULL OR cch.category_id = :categoryId)\n" +
            "  AND (:parentCharacteristicId IS NULL OR cch.parent_id = :parentCharacteristicId)\n" +
            "  AND cch.translations ->> :lang IS NOT NULL\n" +
            "  AND c.translations ->> :lang IS NOT NULL\n" +
            "  AND cch2.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY cch.created_at DESC;", nativeQuery = true)
    Page<CategoryCharacteristicDTO> findAllCustom(@Param("lang") String lang,
                                                  @Param("search") String search,
                                                  @Param("categoryId") Long categoryId,
                                                  @Param("parentCharacteristicId") Long parentCharacteristicId,
                                                  Pageable pageable);

    @Query(value = "SELECT cch.id,\n" +
            "       cch.translations  as names,\n" +
            "       c.id                        as categoryId,\n" +
            "       c.translations ->> :lang    as categoryName,\n" +
            "       cch2.id                     as parentCharacteristicId,\n" +
            "       cch2.translations ->> :lang as parentCharacteristicName\n" +
            "FROM category_characteristics cch\n" +
            "         LEFT JOIN categories c on c.id = cch.category_id\n" +
            "         LEFT JOIN category_characteristics cch2 on cch2.id = cch.parent_id\n" +
            "WHERE c.translations ->> :lang IS NOT NULL\n" +
            "  AND cch2.translations ->> :lang IS NOT NULL\n" +
            "AND cch.id=:id;", nativeQuery = true)
    Optional<CategoryCharacteristicDTO2> findByIdCustom(@Param("lang") String lang,
                                                        @Param("id") Long id);

}
