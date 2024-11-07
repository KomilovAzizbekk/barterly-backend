package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.CharacteristicType;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicTypeDTO;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicTypeResDTO3;

import java.util.List;

public interface CharacteristicTypeRepository extends JpaRepository<CharacteristicType, Long> {

    @Query(value = "SELECT cht.id,\n" +
            "       cht.translations ->> :lang as name,\n" +
            "       c.id                       as categoryId,\n" +
            "       c.translations ->> :lang   as categoryName\n" +
            "FROM characteristic_types cht\n" +
            "         INNER JOIN categories c ON cht.category_id = c.id\n" +
            "WHERE (:search IS NULL OR cht.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:categoryId IS NULL OR c.id = :categoryId)\n" +
            "ORDER BY cht.updated_at DESC", nativeQuery = true)
    Page<CharacteristicTypeDTO> findAllCustom(@Param("lang") String lang,
                                              @Param("search") String search,
                                              @Param("categoryId") Long categoryId,
                                              Pageable pageable);

    List<CharacteristicType> findByCategoryId(Long categoryId);

    @Query(value = "SELECT cht.id,\n" +
            "       cht.translations ->> :lang as name\n" +
            "FROM characteristic_types cht\n" +
            "WHERE cht.category_id = :categoryId\n" +
            "ORDER BY cht.translations ->> :lang", nativeQuery = true)
    List<CharacteristicTypeResDTO3> findByCategoryIdCustom(String lang, Long categoryId);
}
