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
@Table(name = "item_category_characteristics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@DynamicInsert
@DynamicUpdate
public class ItemCategoryCharacteristic extends AbsLongDef {

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private boolean title = false;

    @ManyToOne
    @JoinColumn(name = "category_characteristic_id")
    private CategoryCharacteristic categoryCharacteristic;

    @ManyToOne
    @JoinColumn(name = "category_characteristic_value_id")
    private CategoryCharacteristicValue categoryCharacteristicValue;

}
