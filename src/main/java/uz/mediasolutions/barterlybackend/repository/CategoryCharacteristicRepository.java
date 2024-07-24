package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.CategoryCharacteristic;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CategoryCharacteristicDTO;

public interface CategoryCharacteristicRepository extends JpaRepository<CategoryCharacteristic, Long> {

    @Query(value = "SELECT\n" +
            "    id,\n" +
            "    translations ->> :language as name\n" +
            "FROM\n" +
            "    category_characteristics\n" +
            "WHERE\n" +
            "    translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;\n", nativeQuery = true)
    Page<CategoryCharacteristicDTO> findByByLang(@Param("language") String language,
                                                 Pageable pageable);

}
