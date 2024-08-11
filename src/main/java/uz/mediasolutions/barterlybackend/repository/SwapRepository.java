package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.mediasolutions.barterlybackend.entity.Swap;

import java.util.UUID;

public interface SwapRepository extends JpaRepository<Swap, UUID> {

    @Query(value = "SELECT count(s.id) FROM swaps s WHERE s.requester_user_id = :userId OR s.responder_user_id = :userId", nativeQuery = true)
    Integer findCountByUserId(UUID userId);

}
