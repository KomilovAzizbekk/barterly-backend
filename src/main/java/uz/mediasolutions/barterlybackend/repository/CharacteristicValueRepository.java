package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Characteristic;
import uz.mediasolutions.barterlybackend.entity.CharacteristicValue;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.CharacteristicValueDTO;

public interface CharacteristicValueRepository extends JpaRepository<CharacteristicValue, Long> {

    @Query(value = "SELECT\n" +
            "    id,\n" +
            "    translations ->> :language as name\n" +
            "FROM\n" +
            "    characteristic_values\n" +
            "WHERE\n" +
            "    characteristic_id = :characteristic_id AND\n" +
            "    translations ->> :language IS NOT NULL\n" +
            "ORDER BY created_at DESC;\n", nativeQuery = true)
    Page<CharacteristicValueDTO> findAllByCharacteristicIdOrderByCreatedAtDesc(
            @Param("language") String language,
            Pageable pageable);

}
