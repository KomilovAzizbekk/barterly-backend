package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
