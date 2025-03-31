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
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());

        return userDTO;
    }

    @Override
    public boolean hasUserHitEmail(String email) {

        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
       User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        System.out.println("dsadasadasdasda "+adminAccount);
       if(adminAccount == null){
          User user = new User();

          user.setNic("Test Nic");
          user.setUsername("admin@test.com");
          user.setEmail("admin@test.com");
          user.setPassword(new BCryptPasswordEncoder().encode("admin"));
          user.setFirstName("admin fname");
          user.setLastName("admin lname");
          user.setPhone("070000000");
          user.setIsEnable(true);
           Set<Address> addressList = new HashSet<>();
           addressList.add(new Address("Steet 1","City 1","Province 1","Postal code 1",true));
           addressList.add(new Address("Steet 2","City 2","Province 2","Postal code 2",false));
           user.setAddresses(addressList);
          user.setRole(UserRole.ADMIN);

          userRepository.save(user);
       }
    }


}
