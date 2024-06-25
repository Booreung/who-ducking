package hello.entity.popup;

import hello.entity.user.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "popup_store")
@Getter @Setter
public class PopupStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Embedded
    private Address address;

    // 경도 (x)
    private double longitude;

    // 위도 (y)
    private double latitude;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_name")
    private String imageName;

    @OneToMany(mappedBy = "popupStore")
    private Set<UserPopupStore> userPopupStores = new HashSet<>();
}