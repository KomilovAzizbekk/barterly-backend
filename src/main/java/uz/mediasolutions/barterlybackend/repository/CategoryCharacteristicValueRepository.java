package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryCharacteristicValueDTO;

public interface CategoryCharacteristicValueRepository extends JpaRepository<CategoryCharacteristicValue, Long> {

    @Query(value = "SELECT id,\n" +
            "       translations ->> :language as name\n" +
            "FROM category_characteristic_values\n" +
            "WHERE characteristic_id = :category_characteristic_id\n" +
            "  AND translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;",
    nativeQuery = true)
    Page<CategoryCharacteristicValueDTO> findAllByCategoryCharacteristic(@Param("language") String language,
                                                                         @Param("category_characteristic_id") Long categoryCharacteristicId,
                                                                         Pageable pageable);

}
