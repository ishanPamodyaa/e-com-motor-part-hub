package edu.icet.dto;

import edu.icet.entity.Address;

import edu.icet.utility.UserRole;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String nic;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean isEnabled = true;
    private Set<UserRole> role = new HashSet<>(Set.of(UserRole.CUSTOMER));

    private List<AddressDTO> addressDTOList;


} 