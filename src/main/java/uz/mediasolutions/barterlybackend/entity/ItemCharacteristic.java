package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.barterlybackend.entity.template.AbsLongDef;

/**
 * @author Azizbek Komilov
 * @since 10.07.2024
 */

@Entity
@Table(name = "item_characteristics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@DynamicInsert
@DynamicUpdate
public class ItemCharacteristic extends AbsLongDef {

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private boolean title = false;

    @ManyToOne
    @JoinColumn(name = "characteristic_id")
    private Characteristic characteristic;

    @ManyToOne
    @JoinColumn(name = "characteristic_value_id")
    private CharacteristicValue value;

//    @ManyToOne
//    @JoinColumn(name = "characteristic_type_id")
//    private CharacteristicType characteristicType;

    @Column
    private String textValue;

}
