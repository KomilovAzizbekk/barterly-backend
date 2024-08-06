package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Neighborhood;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.NeighborhoodDTO;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.admin.NeighborhoodDTO2;

import java.util.Optional;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {

    @Query(value = "SELECT n.id,\n" +
            "       n.translations ->> :lang as name,\n" +
            "       r.id                     as regionId,\n" +
            "       r.translations ->> :lang as regionName,\n" +
            "       c.id                     as cityId,\n" +
            "       c.translations ->> :lang as cityName\n" +
            "FROM neighborhoods n\n" +
            "         LEFT JOIN regions r on r.id = n.region_id\n" +
            "         LEFT JOIN cities c on c.id = n.city_id\n" +
            "WHERE (:search IS NULL OR n.translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "  AND (:regionId IS NULL OR r.id = :regionId)\n" +
            "  AND (:cityId IS NULL OR c.id = :cityId)\n" +
            "  AND n.translations ->> :lang IS NOT NULL\n" +
            "  AND r.translations ->> :lang IS NOT NULL\n" +
            "  AND c.translations ->> :lang IS NOT NULL\n" +
            "ORDER BY n.id DESC", nativeQuery = true)
    Page<NeighborhoodDTO> findAllCustom(
            @Param("lang") String lang,
            @Param("search") String search,
            @Param("regionId") Long regionId,
            @Param("cityId") Long cityId,
            Pageable pageable);

    @Query(value = "SELECT n.id,\n" +
            "       n.translations           as names,\n" +
            "       r.id                     as regionId,\n" +
            "       r.translations ->> :lang as regionName,\n" +
            "       c.id                     as cityId,\n" +
            "       c.translations ->> :lang as cityName\n" +
            "FROM neighborhoods n\n" +
            "         LEFT JOIN regions r on r.id = n.region_id\n" +
            "         LEFT JOIN cities c on c.id = n.city_id\n" +
            "WHERE n.id = :id\n" +
            "  AND r.translations ->> :lang IS NOT NULL\n" +
            "  AND c.translations ->> :lang IS NOT NULL", nativeQuery = true)
    Optional<NeighborhoodDTO2> findByIdCustom(@Param("lang") String lang,
                                              @Param("id") Long id);

}
