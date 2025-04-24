package edu.icet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;
    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;




    public  Address(String streetAddress,String city,String province, String postalCode, boolean isDefault ){

        this.streetAddress=streetAddress;
        this.city=city;
        this.province=province;
        this.postalCode=postalCode;
        this.isDefault=isDefault;
    }

}


