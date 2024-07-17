package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Azizbek Komilov
 * @since 16.07.2024
 */

@Entity
@Table(name = "item_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2083)
    private String url;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
