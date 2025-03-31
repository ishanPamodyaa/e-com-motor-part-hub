package edu.icet.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.icet.dto.AuthenticationRequest;
import edu.icet.dto.SignupRequest;
import edu.icet.dto.UserDTO;
import edu.icet.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.auth.AuthService;
import edu.icet.utility.JwtUtil;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    private final AuthService authService;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException {
try {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),authenticationRequest.getPassword()));
}catch (BadCredentialsException e){
    throw new BadCredentialsException("Incorrect UserName Or Password");
}
final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userID" , optionalUser.get().getId())
                    .put("role" , optionalUser.get().getRoles())
                    .toString());
            response.addHeader(HEADER_STRING ,TOKEN_PREFIX + jwt);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser (@RequestBody SignupRequest signupRequest){
        if(authService.hasUserHitEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User All Ready Exists" , HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO userDTO = authService.createUser(signupRequest);

        return new ResponseEntity<>(userDTO , HttpStatus.OK );
    }

}
