package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicDTO2;
import uz.mediasolutions.barterlybackend.payload.response.CharacteristicResDTO;

import java.util.Optional;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    @Query(value = "SELECT ch.id,\n" +
            "       ch.required,\n" +
            "       ch.filter,\n" +
            "       ch.title,\n" +
            "       ch.translations ->> :lang as name,\n" +
            "       c.id                      as categoryId,\n" +
            "       c.translations ->> :lang  as categoryName\n" +
            "FROM characteristics ch\n" +
            "         LEFT JOIN categories c on c.id = ch.category_id\n" +
            "WHERE (:search IS NULL OR ch.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:category_id IS NULL OR c.id = :category_id)\n" +
            "  AND ch.translations ->> :lang IS NOT NULL\n" +
            "  AND c.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY ch.created_at DESC", nativeQuery = true)
    Page<CharacteristicDTO> findAllByOrderByCreatedAtDesc(@Param("lang") String lang,
                                                          @Param("search") String search,
                                                          @Param("category_id") Long categoryId,
                                                          Pageable pageable);

    @Query(value = "SELECT ch.id,\n" +
            "       ch.required,\n" +
            "       ch.filter,\n" +
            "       ch.title,\n" +
            "       ch.translations          as names,\n" +
            "       c.id                     as categoryId,\n" +
            "       c.translations ->> :lang as categoryName\n" +
            "FROM characteristics ch\n" +
            "         LEFT JOIN categories c on c.id = ch.category_id\n" +
            "WHERE ch.id = :id\n" +
            "  AND c.translations ->> :lang IS NOT NULL", nativeQuery = true)
    Optional<CharacteristicDTO2> findByIdCustom(@Param("lang") String lang,
                                                @Param("id") Long id);

}
