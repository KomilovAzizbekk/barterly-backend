package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleEnum name);

}

