package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CharacteristicDTO;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    @Query(value = "SELECT ch.id,\n" +
            "       ch.filter,\n" +
            "       ch.title,\n" +
            "       ch.translations ->> :lang  as name,\n" +
            "       c.id                       as categoryId,\n" +
            "       c.translations ->> :lang   as categoryName,\n" +
            "       cht.id                     as characteristicTypeId,\n" +
            "       cht.translations ->> :lang as characteristicTypeName\n" +
            "FROM characteristics ch\n" +
            "         INNER JOIN categories c on c.id = ch.category_id\n" +
            "         INNER JOIN characteristic_types cht on ch.characteristic_type_id = cht.id\n" +
            "WHERE (:search IS NULL OR ch.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:category_id IS NULL OR c.id = :category_id)\n" +
            "  AND (:characteristic_type_id IS NULL OR cht.id = :characteristic_type_id)\n" +
            "ORDER BY ch.updated_at DESC", nativeQuery = true)
    Page<CharacteristicDTO> findAllByOrderByUpdatedAtDesc(@Param("lang") String lang,
                                                          @Param("search") String search,
                                                          @Param("category_id") Long categoryId,
                                                          @Param("characteristic_type_id") Long characteristicTypeId,
                                                          Pageable pageable);


}
