package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsUUID;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;

import java.math.BigDecimal;

/**
 * @author Azizbek Komilov
 * @since 13.06.2024
 */

@Entity
@Table(name = "swaps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Swap extends AbsUUID {

    @ManyToOne
    @JoinColumn(name = "requester_user_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "responder_user_id")
    private User responder;

    @ManyToOne
    @JoinColumn(name = "requester_item_id")
    private Item requesterItem;

    @ManyToOne
    @JoinColumn(name = "responder_item_id")
    private Item responderItem;

    @Column(nullable = false, length = 300)
    private String message;

    @ManyToOne
    @JoinColumn(name = "swap_status_id")
    private SwapStatus swapStatus;

}
