package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsUUID;

/**
 * @author Azizbek Komilov
 * @since 24.06.2024
 */

@Entity
@Table(name = "reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Report extends AbsUUID {

    @ManyToOne
    @JoinColumn(name = "reporter_user_id")
    private User reporterUser;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false, length = 500)
    private String description;

    // report status

    @ManyToOne
    @JoinColumn(name = "assigned_admin_id")
    private User assignedTo;

}
