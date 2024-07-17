package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mediasolutions.barterlybackend.enums.ContentTypeEnum;

/**
 * @author Azizbek Komilov
 * @since 26.06.2024
 */

@Entity
@Table(name = "content_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentTypeEnum name;
}
