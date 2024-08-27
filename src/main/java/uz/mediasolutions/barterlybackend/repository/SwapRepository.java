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

    @Query(value = "SELECT s.id,\n" +
            "       ss.name as status,\n" +
            "       s.created_at AS createdAt,\n" +
            "       CASE\n" +
            "           WHEN responder.id != :userId THEN responder.username\n" +
            "           ELSE requester.username\n" +
            "           END      AS username,\n" +
            "       CASE\n" +
            "           WHEN i1.user_id = :userId THEN i1.description\n" +
            "           ELSE i2.description\n" +
            "           END      AS title1,\n" +
            "       CASE\n" +
            "           WHEN i1.user_id != :userId THEN i1.description\n" +
            "           ELSE i2.description\n" +
            "           END      AS title2\n" +
            "FROM swaps s\n" +
            "         LEFT JOIN swap_statuses ss ON s.swap_status_id = ss.id\n" +
            "         LEFT JOIN users requester ON requester.id = s.requester_user_id\n" +
            "         LEFT JOIN users responder ON responder.id = s.responder_user_id\n" +
            "         LEFT JOIN items i1 ON i1.id = s.requester_item_id\n" +
            "         LEFT JOIN items i2 ON i2.id = s.responder_item_id\n" +
            "WHERE requester.id = :userId\n" +
            "   OR responder.id = :userId\n" +
            "ORDER BY s.created_at DESC", nativeQuery = true)
    Page<SwapDTO> findAllByUserId(@Param("userId") UUID userId,
//                                  @Param("lang") String lang,
                                  Pageable pageable);

    Page<Swap> findAllByRequesterIdOrResponderId(UUID userId, UUID userId1, Pageable pageable);

}
