package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Swap;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.SwapDTO;

import java.util.UUID;

public interface SwapRepository extends JpaRepository<Swap, UUID> {

    @Query(value = "SELECT count(s.id) FROM swaps s WHERE s.requester_user_id = :userId OR s.responder_user_id = :userId", nativeQuery = true)
    Integer findCountByUserId(UUID userId);

    @Query(value = "(SELECT s.id,\n" +
            "        s.swap_status AS status,\n" +
            "        s.created_at  AS createdAt,\n" +
            "        responder.username AS username,\n" +
            "        (CASE WHEN i1.user_id = :userId THEN i1.title ->> :lang ELSE i2.title ->> :lang END) AS title1,\n" +
            "        (CASE WHEN i1.user_id != :userId THEN i1.title ->> :lang ELSE i2.title ->> :lang END) AS title2\n" +
            " FROM swaps s\n" +
            "          INNER JOIN users requester ON requester.id = s.requester_user_id\n" +
            "          INNER JOIN users responder ON responder.id = s.responder_user_id\n" +
            "          INNER JOIN items i1 ON i1.id = s.requester_item_id\n" +
            "          INNER JOIN items i2 ON i2.id = s.responder_item_id\n" +
            " WHERE requester.id = :userId)\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "(SELECT s.id,\n" +
            "        s.swap_status AS status,\n" +
            "        s.created_at  AS createdAt,\n" +
            "        requester.username AS username,\n" +
            "        (CASE WHEN i1.user_id = :userId THEN i1.title ->> :lang ELSE i2.title ->> :lang END) AS title1,\n" +
            "        (CASE WHEN i1.user_id != :userId THEN i1.title ->> :lang ELSE i2.title ->> :lang END) AS title2\n" +
            " FROM swaps s\n" +
            "          INNER JOIN users requester ON requester.id = s.requester_user_id\n" +
            "          INNER JOIN users responder ON responder.id = s.responder_user_id\n" +
            "          INNER JOIN items i1 ON i1.id = s.requester_item_id\n" +
            "          INNER JOIN items i2 ON i2.id = s.responder_item_id\n" +
            " WHERE responder.id = :userId)\n" +
            "ORDER BY createdAt DESC", nativeQuery = true)
    Page<SwapDTO> findAllByUserId(@Param("userId") UUID userId,
                                  @Param("lang") String lang,
                                  Pageable pageable);

}
