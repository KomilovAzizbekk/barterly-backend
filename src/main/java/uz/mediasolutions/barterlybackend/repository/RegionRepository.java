package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Region;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.RegionDTO;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = "SELECT r.id,\n" +
            "       r.image_url,\n" +
            "       json_build_object(\n" +
            "               'id', c.id,\n" +
            "               'name', c.translations ->> :language,\n" +
            "               'currency_code', c.currency_code\n" +
            "       )                            as currency,\n" +
            "       r.translations ->> :language as name\n" +
            "FROM regions r\n" +
            "         LEFT JOIN currencies c on c.id = r.default_currency_id\n" +
            "WHERE r.translations ->> :language IS NOT NULL\n" +
            "  AND (c.translations ->> :language IS NOT NULL OR c.id IS NULL)\n" +
            "ORDER BY name;",
            nativeQuery = true)
    Page<RegionDTO> findAllByOrderByNameAsc(@Param("language") String language,
                                            Pageable pageable);

}
