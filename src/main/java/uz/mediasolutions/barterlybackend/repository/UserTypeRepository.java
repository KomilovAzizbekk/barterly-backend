package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
