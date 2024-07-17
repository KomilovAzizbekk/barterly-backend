package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsLongDef;

/**
 * @author Azizbek Komilov
 * @since 24.06.2024
 */

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ExchangeRate extends AbsLongDef {

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency fromCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency toCurrency;

    private Double rate;

}
