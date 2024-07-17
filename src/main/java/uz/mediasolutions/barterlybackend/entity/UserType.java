package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsLongDef;
import uz.mediasolutions.barterlybackend.enums.UserTypeEnum;

import java.util.List;

/**
 * @author Azizbek Komilov
 * @since 16.06.2024
 */

@Entity
@Table(name = "user_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserType extends AbsLongDef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserTypeEnum name;

    @OneToMany(mappedBy = "userType", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<User> users;

    public UserType(UserTypeEnum name) {
        this.name = name;
    }

}
