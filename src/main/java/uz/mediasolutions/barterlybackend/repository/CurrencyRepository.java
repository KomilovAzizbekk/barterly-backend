package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CurrencyDTO;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query(value = "SELECT id,\n" +
            "       currency_code          as currencyCode,\n" +
            "       image_url              as imageUrl,\n" +
            "       translations ->> :lang as name\n" +
            "FROM currencies\n" +
            "WHERE translations ->> :lang IS NOT NULL\n" +
            "  AND (:search IS NULL OR translations ->> :lang ILIKE '%' || :search || '%')\n" +
            "ORDER BY created_at DESC", nativeQuery = true)
    Page<CurrencyDTO> findAllCustom(@Param("lang") String lang,
                                    @Param("search") String search,
                                    Pageable pageable);

}
