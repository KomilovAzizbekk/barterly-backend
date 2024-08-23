package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;
import uz.mediasolutions.barterlybackend.enums.UserTypeEnum;

import java.util.List;

/**
 * @author Azizbek Komilov
 * @since 16.06.2024
 */

@Entity
@Table(name = "swap_statuses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SwapStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwapStatusEnum name;

    @OneToMany(mappedBy = "swapStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Swap> swaps;

    public SwapStatus(SwapStatusEnum value) {
        this.name = value;
    }
}
