package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.RegionDTO;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = "SELECT r.id,\n" +
            "       r.image_url                  as imageUrl,\n" +
            "       c.id                         as currencyId,\n" +
            "       c.currency_code              as currencyCode,\n" +
            "       r.translations ->> :lang as name\n" +
            "FROM regions r\n" +
            "         LEFT JOIN currencies c on c.id = r.default_currency_id\n" +
            "WHERE (:search IS NULL OR r.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:currencyId IS NULL OR r.default_currency_id = :currencyId)\n" +
            "  AND r.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY r.id DESC",
            nativeQuery = true)
    Page<RegionDTO> findAllCustom(@Param("lang") String lang,
                                            @Param("search") String search,
                                            @Param("currencyId") Long currencyId,
                                            Pageable pageable);

//    @Query(value = "SELECT r.id,\n" +
//            "       r.image_url     as imageUrl,\n" +
//            "       c.id            as currencyId,\n" +
//            "       c.currency_code as currencyCode,\n" +
//            "       r.translations  as names\n" +
//            "FROM regions r\n" +
//            "         LEFT JOIN currencies c on c.id = r.default_currency_id\n" +
//            "WHERE r.id = :id", nativeQuery = true)
//    Optional<RegionDTO2> findByIdCustom(@Param("id") Long id);

}
