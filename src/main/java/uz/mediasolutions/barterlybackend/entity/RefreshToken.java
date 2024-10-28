package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * @author Azizbek Komilov
 * @since 04.07.2024
 */

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    private Date expireDate;

}
