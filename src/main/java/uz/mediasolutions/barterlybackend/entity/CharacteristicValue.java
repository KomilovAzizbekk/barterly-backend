package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.mediasolutions.barterlybackend.entity.template.AbsLongDef;

import java.util.List;
import java.util.Map;

/**
 * @author Azizbek Komilov
 * @since 10.07.2024
 */

@Entity
@Table(name = "characteristic_values")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@DynamicInsert
@DynamicUpdate
public class CharacteristicValue extends AbsLongDef {

    @ManyToOne
    @JoinColumn(name = "characteristic_id")
    private Characteristic characteristic;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> translations;

    @OneToMany(mappedBy = "value", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<ItemCharacteristic> itemCharacteristics;

}
