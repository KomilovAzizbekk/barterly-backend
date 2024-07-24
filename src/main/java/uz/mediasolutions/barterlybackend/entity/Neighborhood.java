package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.mediasolutions.barterlybackend.entity.template.AbsAudit;

import java.util.List;
import java.util.Map;

/**
 * @author Azizbek Komilov
 * @since 16.06.2024
 */

@Entity
@Table(name = "neighborhoods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Neighborhood extends AbsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> translations;

}
