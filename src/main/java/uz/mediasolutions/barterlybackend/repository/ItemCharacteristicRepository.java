package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.ItemCharacteristic;

public interface ItemCharacteristicRepository extends JpaRepository<ItemCharacteristic, Long> {
}
