package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Currency;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CurrencyDTO;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query(value = "SELECT\n" +
            "    id,\n" +
            "    currency_code as currencyCode,\n" +
            "    image_url as imageUrl,\n" +
            "    translations ->> :language as name\n" +
            "FROM\n" +
            "    currencies\n" +
            "WHERE\n" +
            "    translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;\n", nativeQuery = true)
    Page<CurrencyDTO> findAllByOrderByCreatedAtDesc(@Param("language") String language,
                                                    Pageable pageable);

}
