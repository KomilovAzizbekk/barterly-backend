package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsAudit;

/**
 * @author Azizbek Komilov
 * @since 13.06.2024
 */

@Entity
@Table(name = "languages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Language extends AbsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

}
