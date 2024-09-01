package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.ItemCharacteristic;

import java.util.Optional;
import java.util.UUID;

public interface ItemCharacteristicRepository extends JpaRepository<ItemCharacteristic, Long> {

    Optional<ItemCharacteristic> findByItemIdAndCharacteristicId(UUID itemId, Long characteristicId);

}
