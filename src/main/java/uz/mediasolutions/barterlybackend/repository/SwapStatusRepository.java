package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.SwapStatus;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;

public interface SwapStatusRepository extends JpaRepository<SwapStatus, Long> {

    SwapStatus findByName(SwapStatusEnum name);

}
