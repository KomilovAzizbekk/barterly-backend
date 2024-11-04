package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import uz.mediasolutions.barterlybackend.entity.template.AbsAuditDeleted;
import uz.mediasolutions.barterlybackend.entity.template.AbsDateDeleted;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Azizbek Komilov
 * @since 13.06.2024
 */

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE items SET deleted = true WHERE id=?")
@Builder
@DynamicInsert
@DynamicUpdate
public class Item extends AbsAuditDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 600)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean active = true;

    private boolean premium = false;

    private boolean temporary = true;

    private Timestamp temporaryToDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> title;

    @ManyToOne
    @JoinColumn(name = "item_status_id")
    private ItemStatus itemStatus;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<ItemCharacteristic> itemCharacteristics;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<ItemImage> itemImages;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    @BatchSize(size = 10)
    private List<ItemCategoryCharacteristic> itemCategoryCharacteristics;

}
