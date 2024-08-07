package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.mediasolutions.barterlybackend.entity.template.AbsUUID;
import uz.mediasolutions.barterlybackend.enums.UserSocketStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author Azizbek Komilov
 * @since 13.06.2024
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class User extends AbsUUID implements UserDetails {

    private String name;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private UserSocketStatusEnum socketStatus;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Conversation> conversationsUser1;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Conversation> conversationsUser2;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "neighborhood_id")
    private Neighborhood neighborhood;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Avoid potential issues with circular references
    private List<Item> items;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Review> reviewsSender;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Review> reviewsReceiver;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Swap> swapsRequester;

    @OneToMany(mappedBy = "responder", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Swap> swapsResponder;

    @OneToMany(mappedBy = "reporterUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Report> reportsReporter;

    @OneToMany(mappedBy = "reportedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Report> reportsReported;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // These 4 fields are created for user details
    @Column(nullable = false)
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    private boolean enabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(role);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
