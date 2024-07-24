package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicDTO;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    @Query(value = "SELECT ch.id,\n" +
            "       required,\n" +
            "       ch.translations ->> :language as name,\n" +
            "       c.id                          as categoryId,\n" +
            "       c.translations ->> :language  as categoryName,\n" +
            "       cc.id                         as categoryCharacteristicId,\n" +
            "       cc.translations ->> :language as categoryCharacteristicName\n" +
            "FROM characteristics ch\n" +
            "         LEFT JOIN categories c on c.id = ch.category_id\n" +
            "         LEFT JOIN category_characteristics cc on cc.id = ch.category_characteristic_id\n" +
            "WHERE ch.translations ->> :language IS NOT NULL\n" +
            "  AND (c.translations ->> :language IS NOT NULL OR c.id IS NULL)\n" +
            "  AND (cc.translations ->> :language IS NOT NULL OR cc.id IS NULL)\n" +
            "ORDER BY ch.created_at DESC;", nativeQuery = true)
    Page<CharacteristicDTO> findAllByOrderByCreatedAtDesc(
            @Param("language") String language,
            Pageable pageable);

}
