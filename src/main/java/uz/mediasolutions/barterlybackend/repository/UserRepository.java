package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.enums.UserSocketStatusEnum;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(String username);

    List<User> findAllBySocketStatus(UserSocketStatusEnum socketStatus);

    @Query(value = "SELECT id,\n" +
            "       phone_number,\n" +
            "       username,\n" +
            "       role_id\n" +
            "FROM users\n" +
            "WHERE ((:search IS NULL OR name ILIKE '%' || :search || '%')\n" +
            "   OR (:search IS NULL OR username ILIKE '%' || :search || '%'))\n" +
            "    AND role_id = :roleId\n" +
            "ORDER BY created_at DESC", nativeQuery = true)
    Page<UserResDTO> findAllUsersCustom(UUID roleId, String search, Pageable pageable);

    @Query(value = "SELECT u.id,\n" +
            "       u.username,\n" +
            "       u.role_id as roleId,\n" +
            "       r.name as role\n" +
            "FROM users u\n" +
            "JOIN roles r on u.role_id = r.id\n" +
            "WHERE ((:search IS NULL OR u.name ILIKE '%' || :search || '%')\n" +
            "   OR (:search IS NULL OR username ILIKE '%' || :search || '%'))\n" +
            "    AND role_id != :roleId\n" +
            "ORDER BY u.created_at DESC", nativeQuery = true)
    Page<AdminResDTO> findAllAdminsCustom(UUID roleId, String search, Pageable pageable);

    boolean existsByUsername(String username);
}
