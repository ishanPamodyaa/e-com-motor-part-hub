package edu.icet.service.impl;

import edu.icet.dto.AddressDTO;
import edu.icet.dto.UserDTO;
import edu.icet.entity.Address;
import edu.icet.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = new User();

//        user.setId(userDTO.getId());
        user.setNic(userDTO.getNic());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setIsEnable(userDTO.isEnabled());
        user.setRole(userDTO.getRole());

         Set<Address> addressList = new HashSet<>();
        List<AddressDTO> addressDTOList = userDTO.getAddressDTOList();

        System.out.println("sdsdsdsd"+ addressDTOList);

        for(AddressDTO addressDTO : addressDTOList){
            Address address = new Address();
                    address.setStreetAddress(addressDTO.getStreetAddress());
                    address.setCity(addressDTO.getCity());
                    address.setProvince(addressDTO.getProvince());
                    address.setPostalCode(addressDTO.getPostalCode());
                    address.setDefault(addressDTO.isDefault());
                    address.setUser(user);
            addressList.add(address);
        }
        user.setAddresses(addressList);

        userRepository.save(user);

        return userDTO;
    }
}
