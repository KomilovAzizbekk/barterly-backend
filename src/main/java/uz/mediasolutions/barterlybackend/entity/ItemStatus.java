package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mediasolutions.barterlybackend.enums.ItemStatusEnum;
import uz.mediasolutions.barterlybackend.enums.UserTypeEnum;

import java.util.List;

/**
 * @author Azizbek Komilov
 * @since 16.06.2024
 */

@Entity
@Table(name = "item_statuses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatusEnum name;

    @OneToMany(mappedBy = "itemStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

}
