package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class ItemCategoryCharacteristic extends AbsLongDef {

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "category_characteristic_id")
    private CategoryCharacteristic categoryCharacteristic;

    @ManyToOne
    @JoinColumn(name = "category_characteristic_value_id")
    private CategoryCharacteristicValue categoryCharacteristicValue;

}
