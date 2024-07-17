package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicDTO;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    @Query(value = "SELECT\n" +
            "    id,\n" +
            "    required,\n" +
            "    translations ->> :language as name\n" +
            "FROM\n" +
            "    characteristics\n" +
            "WHERE\n" +
            "    translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;\n", nativeQuery = true)
    Page<CharacteristicDTO> findAllByOrderByCreatedAtDesc(
            @Param("language") String language,
            Pageable pageable);

}
