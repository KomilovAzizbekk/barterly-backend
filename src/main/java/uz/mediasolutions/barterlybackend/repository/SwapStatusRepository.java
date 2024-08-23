package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.SwapStatus;

public interface SwapStatusRepository extends JpaRepository<SwapStatus, Long> {
}
