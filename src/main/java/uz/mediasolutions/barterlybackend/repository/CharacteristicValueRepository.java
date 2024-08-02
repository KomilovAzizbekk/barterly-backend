package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.CharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicValueDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicValueDTO2;

import java.util.Optional;

public interface CharacteristicValueRepository extends JpaRepository<CharacteristicValue, Long> {

    @Query(value = "SELECT cv.id,\n" +
            "       cv.translations ->> :lang as name,\n" +
            "       ch.id                     as characteristicId,\n" +
            "       ch.translations ->> :lang as characteristicName\n" +
            "FROM characteristic_values cv\n" +
            "         LEFT JOIN characteristics ch on ch.id = cv.characteristic_id\n" +
            "WHERE (:search IS NULL OR cv.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:characteristic_id IS NULL OR cv.characteristic_id=:characteristic_id)\n" +
            "  AND cv.translations ->> :lang IS NOT NULL\n" +
            "  AND ch.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY cv.created_at DESC", nativeQuery = true)
    Page<CharacteristicValueDTO> findAllCustom(
            @Param("lang") String lang,
            @Param("search") String search,
            @Param("characteristic_id") Long characteristicId,
            Pageable pageable);

    @Query(value = "SELECT cv.id,\n" +
            "       cv.translations           as names,\n" +
            "       ch.id                     as characteristicId,\n" +
            "       ch.translations ->> :lang as characteristicName\n" +
            "FROM characteristic_values cv\n" +
            "         LEFT JOIN characteristics ch on ch.id = cv.characteristic_id\n" +
            "WHERE cv.id = :id\n" +
            "  AND ch.translations ->> :lang IS NOT NULL", nativeQuery = true)
    Optional<CharacteristicValueDTO2> findByIdCustom(
            @Param("lang") String lang,
            @Param("id") Long id);

}
