package edu.icet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;
    private boolean isDefault;

}
