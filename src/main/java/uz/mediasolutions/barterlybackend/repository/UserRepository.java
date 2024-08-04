package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.enums.UserSocketStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(String username);

    List<User> findAllBySocketStatus(UserSocketStatusEnum socketStatus);

    Page<User> findAllByRoleNameOrderByCreatedAtDesc(RoleEnum role, Pageable pageable);

    Page<User> findAllByRoleNameIsNotOrderByCreatedAtDesc(RoleEnum role, Pageable pageable);

    boolean existsByUsername(String username);
}
