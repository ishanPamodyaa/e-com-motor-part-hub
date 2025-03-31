package edu.icet.service.impl;

import edu.icet.dto.SignupRequest;
import edu.icet.dto.UserDTO;
import edu.icet.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.auth.AuthService;
import edu.icet.utility.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserDTO createUser (SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRoles(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());

        return userDTO;
    }

    @Override
    public boolean hasUserHitEmail(String email) {
        return false;
    }


}
