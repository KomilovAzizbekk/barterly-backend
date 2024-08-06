package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.City;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CityDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.CityDTO2;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "SELECT c.id,\n" +
            "       c.translations ->> :lang as name,\n" +
            "       r.id                     as regionId,\n" +
            "       r.translations ->> :lang as regionName\n" +
            "FROM cities c\n" +
            "         LEFT JOIN regions r on r.id = c.region_id\n" +
            "WHERE (:search IS NULL OR c.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:regionId IS NULL OR r.id = :regionId)\n" +
            "  AND c.translations ->> :lang IS NOT NULL\n" +
            "  AND r.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY c.id DESC", nativeQuery = true)
    Page<CityDTO> findAllCustom(
            @Param("lang") String lang,
            @Param("search") String search,
            @Param("regionId") Long regionId,
            Pageable pageable);

    @Query(value = "SELECT c.id,\n" +
            "       c.translations           as names,\n" +
            "       r.id                     as regionId,\n" +
            "       r.translations ->> :lang as regionName\n" +
            "FROM cities c\n" +
            "         LEFT JOIN regions r on r.id = c.region_id\n" +
            "WHERE c.id = :id\n" +
            "  AND r.translations ->> :lang IS NOT NULL", nativeQuery = true)
    Optional<CityDTO2> findByIdCustom(@Param("lang") String lang,
                                      @Param("id") Long id);

}
