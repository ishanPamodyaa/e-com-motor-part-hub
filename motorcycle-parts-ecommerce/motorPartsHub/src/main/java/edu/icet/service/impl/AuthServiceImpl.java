package edu.icet.service.impl;

import edu.icet.dto.SignupRequest;
import edu.icet.dto.UserDTO;
import edu.icet.entity.Address;
import edu.icet.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.auth.AuthService;
import edu.icet.utility.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

//    private UserRepository userRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO createUser (SignupRequest signupRequest){
        User user = new User();


        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getName());
        // Use the injected bCryptPasswordEncoder instead of creating new instance
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        
        // Add these fields from signupRequest
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPhone(signupRequest.getContactNumber());
        user.setNic(signupRequest.getNic());
        user.setIsEnable(true);

        // Create and set address
        Set<Address> addresses = new HashSet<>();
        Address address = new Address(
            signupRequest.getAddress(),
            signupRequest.getCity(),
            signupRequest.getProvince(),
            signupRequest.getPostalCode(),
            true  // Set as primary address
        );
        addresses.add(address);
        user.setAddresses(addresses);

        User createdUser = userRepository.save(user);

        // Map more fields to DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setUsername(createdUser.getUsername());

        
        // user.setEmail(signupRequest.getEmail());
        // user.setUsername(signupRequest.getName());
        // user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        // user.setRole(UserRole.CUSTOMER);
        // User createdUser = userRepository.save(user);

        // UserDTO userDTO = new UserDTO();
        // userDTO.setId(createdUser.getId());

        return userDTO;
    }

    @Override
    public boolean hasUserHitEmail(String email) {

        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        try {
            User adminAccount = userRepository.findByRole(UserRole.ADMIN);
            if(adminAccount == null){
                User user = new User();
                user.setNic("Test Nic");
                user.setUsername("admin@test.com");
                user.setEmail("admin@test.com");
                user.setPassword(bCryptPasswordEncoder.encode("admin")); // Use injected encoder
                user.setFirstName("admin fname");
                user.setLastName("admin lname");
                user.setPhone("070000000");
                user.setIsEnable(true);
                
                Set<Address> addressList = new HashSet<>();
                Address address = new Address();
                address.setStreetAddress("Street 1");
                address.setCity("City 1");
                address.setProvince("Province 1");
                address.setPostalCode("Postal code 1");
                address.setDefault(false);
                addressList.add(address);
                
                user.setAddresses(addressList);
                user.setRole(UserRole.ADMIN);
                
                userRepository.save(user);
            }
        } catch (Exception e) {
            // Log the error but don't prevent application startup
            System.err.println("Error creating admin account: " + e.getMessage());
        }
    }

}
