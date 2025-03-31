package edu.icet.service.auth;

import edu.icet.dto.SignupRequest;
import edu.icet.dto.UserDTO;

public interface AuthService {

    UserDTO createUser (SignupRequest signupRequest);

    boolean hasUserHitEmail(String email);
}
