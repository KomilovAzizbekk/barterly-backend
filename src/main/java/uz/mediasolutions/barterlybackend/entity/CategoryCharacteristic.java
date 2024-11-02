package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.mediasolutions.barterlybackend.entity.template.AbsDate;

import java.util.*;

/**
 * @author Azizbek Komilov
 * @since 10.07.2024
 */

@Entity
@Table(name = "category_characteristics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryCharacteristic extends AbsDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean title = false;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryCharacteristic parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<CategoryCharacteristic> children = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> translations;

    @OneToMany(mappedBy = "characteristic", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<CategoryCharacteristicValue> categoryCharacteristicValues = new ArrayList<>();

    @OneToMany(mappedBy = "categoryCharacteristic", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<ItemCategoryCharacteristic> itemCategoryCharacteristics = new ArrayList<>();

}
