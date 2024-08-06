package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryCharacteristicValueDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CategoryCharacteristicValueDTO2;

import java.util.Optional;

public interface CategoryCharacteristicValueRepository extends JpaRepository<CategoryCharacteristicValue, Long> {

    @Query(value = "SELECT ccv.id,\n" +
            "       ccv.translations ->> :lang as name,\n" +
            "       cc.id                      as categoryCharacteristicId,\n" +
            "       cc.translations ->> :lang  as categoryCharacteristicName\n" +
            "FROM category_characteristic_values ccv\n" +
            "         LEFT JOIN category_characteristics cc on cc.id = ccv.characteristic_id\n" +
            "WHERE (:search IS NULL OR ccv.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:category_characteristic_id IS NULL OR cc.id = :category_characteristic_id)\n" +
            "  AND cc.translations ->> :lang IS NOT NULL\n" +
            "  AND ccv.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY ccv.created_at DESC",
            nativeQuery = true)
    Page<CategoryCharacteristicValueDTO> findAll(@Param("lang") String lang,
                                                 @Param("category_characteristic_id") Long categoryCharacteristicId,
                                                 @Param("search") String search,
                                                 Pageable pageable);

    @Query(value = "SELECT ccv.id,\n" +
            "       ccv.translations          as names,\n" +
            "       cc.id                     as categoryCharacteristicId,\n" +
            "       cc.translations ->> :lang as categoryCharacteristicName\n" +
            "FROM category_characteristic_values ccv\n" +
            "         LEFT JOIN category_characteristics cc on cc.id = ccv.characteristic_id\n" +
            "WHERE cc.translations ->> :lang IS NOT NULL\n" +
            "  AND ccv.id = :id", nativeQuery = true)
    Optional<CategoryCharacteristicValueDTO2> findByIdCustom(@Param("lang") String lang,
                                                             @Param("id") Long id);

}
