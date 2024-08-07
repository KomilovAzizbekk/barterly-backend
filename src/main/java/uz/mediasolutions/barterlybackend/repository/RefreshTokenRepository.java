package uz.mediasolutions.barterlybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.barterlybackend.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    boolean existsByToken(String token);

    boolean existsByUserId(UUID userId);

    RefreshToken findByUserId(UUID userId);

}
