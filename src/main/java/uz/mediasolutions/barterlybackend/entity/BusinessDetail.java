package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsLongDef;

/**
 * @author Azizbek Komilov
 * @since 15.06.2024
 */

@Entity
@Table(name = "business_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessDetail extends AbsLongDef {

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String businessAddress;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
