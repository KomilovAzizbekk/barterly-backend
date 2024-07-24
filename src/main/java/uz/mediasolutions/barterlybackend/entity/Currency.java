package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.mediasolutions.barterlybackend.entity.template.AbsAudit;
import uz.mediasolutions.barterlybackend.entity.template.AbsDate;

import java.util.List;
import java.util.Map;

/**
 * @author Azizbek Komilov
 * @since 16.06.2024
 */

@Entity
@Table(name = "currencies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Currency extends AbsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String currencyCode;

    //Currency symbol
    @Column(length = 2083)
    private String imageUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> translations;

    @OneToMany(mappedBy = "fromCurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ExchangeRate> exchangeRatesFrom;

    @OneToMany(mappedBy = "toCurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ExchangeRate> exchangeRatesTo;

}
