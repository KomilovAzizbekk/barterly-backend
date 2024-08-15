package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
