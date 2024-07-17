package uz.mediasolutions.barterlybackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.mediasolutions.barterlybackend.entity.template.AbsDate;
import uz.mediasolutions.barterlybackend.entity.template.AbsUUID;

import java.util.List;

/**
 * @author Azizbek Komilov
 * @since 24.06.2024
 */

@Entity
@Table(name = "contents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Content extends AbsUUID {

    @ManyToOne
    @JoinColumn(name = "content_type_id")
    private ContentType contentType;

    //If content is IMAGE or VIDEO url is used. Else null.
    @Column(length = 2083)
    private String url;

    private String text;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;
}
