package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.mediasolutions.barterlybackend.entity.Role;
import uz.mediasolutions.barterlybackend.entity.User;
import uz.mediasolutions.barterlybackend.enums.RoleEnum;
import uz.mediasolutions.barterlybackend.enums.UserSocketStatusEnum;
import uz.mediasolutions.barterlybackend.payload.interfaceDTO.user.ProfileDTO;
import uz.mediasolutions.barterlybackend.payload.response.AdminResDTO;
import uz.mediasolutions.barterlybackend.payload.response.UserResDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(String phoneNumber);

    Optional<User> findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(String username);

    Optional<User> findFirstByIdAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(UUID id);

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

    @Query(value = "SELECT u.id,\n" +
            "       u.name,\n" +
            "       u.username,\n" +
            "       u.level,\n" +
            "       u.phone_number                                      as phoneNumber,\n" +
            "       COUNT(CASE WHEN s.swap_status_id = 1 THEN s.id END) AS offers,\n" +
            "       COUNT(CASE WHEN s.swap_status_id = 2 THEN s.id END) AS swaps,\n" +
            "       u.created_at                                        AS createdAt,\n" +
            "       c.translations ->> :lang                            AS city,\n" +
            "       n.translations ->> :lang                            AS neighborhood\n" +
            "FROM users u\n" +
            "         LEFT JOIN cities c ON u.city_id = c.id\n" +
            "         LEFT JOIN neighborhoods n ON u.neighborhood_id = n.id\n" +
            "         LEFT JOIN swaps s ON u.id = s.responder_user_id\n" +
            "WHERE u.id = :id\n" +
            "GROUP BY u.id, u.name, u.username, u.level, u.phone_number, u.created_at, c.translations, n.translations", nativeQuery = true)
    ProfileDTO getUserProfileInfo(@Param("lang") String lang,
                                  @Param("id") UUID id);
}
