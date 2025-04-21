package edu.icet.dto;

import edu.icet.utility.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String nic;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean isEnabled = true;
    private UserRole role ;

    private List<AddressDTO> addressDTOList;


} 