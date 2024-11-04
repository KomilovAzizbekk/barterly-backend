package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsAuditDeleted;
import uz.mediasolutions.barterlybackend.entity.template.AbsUUID;
import uz.mediasolutions.barterlybackend.enums.SwapStatusEnum;

import java.util.UUID;

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
public class Swap extends AbsAuditDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "requester_user_id", nullable = false)
    private UUID requesterUserId;

    @Column(name = "responder_user_id", nullable = false)
    private UUID responderUserId;

    @Column(name = "requester_item_id", nullable = false)
    private UUID requesterItemId;

    @Column(name = "responder_item_id", nullable = false)
    private UUID responderItemId;

    @Column(nullable = false, length = 300)
    private String message;

    @Column(name = "swap_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SwapStatusEnum swapStatus;

}
