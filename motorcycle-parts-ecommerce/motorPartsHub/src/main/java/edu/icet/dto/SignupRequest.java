package edu.icet.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String name;
    private String password;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String nic;
    private String address;
    private String city;
    private String province;
    private String district;
    private String postalCode;
}
