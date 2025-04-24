package edu.icet.controller;

import edu.icet.dto.UserDTO;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {
    final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO ){
        UserDTO createdUser = userService.createUser(userDTO);

        return ResponseEntity.ok(createdUser);
    }

//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        List<UserDTO> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
//        UserDTO user = userService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/username/{username}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
//        UserDTO user = userService.getUserByUsername(username);
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping("/email/{email}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
//        UserDTO user = userService.getUserByEmail(email);
//        return ResponseEntity.ok(user);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
//        UserDTO updatedUser = userService.updateUser(id, userDTO);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    @PostMapping("/{id}/change-password")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<MessageResponse> changePassword(
//            @PathVariable Long id,
//            @RequestBody Map<String, String> passwordData) {
//
//        String currentPassword = passwordData.get("currentPassword");
//        String newPassword = passwordData.get("newPassword");
//
//        if (currentPassword == null || newPassword == null) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Current password and new password are required"));
//        }
//
//        userService.changePassword(id, currentPassword, newPassword);
//        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody Map<String, String> emailData) {
//        String email = emailData.get("email");
//
//        if (email == null) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Email is required"));
//        }
//
//        userService.initiatePasswordReset(email);
//        return ResponseEntity.ok(new MessageResponse("Password reset email sent"));
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<MessageResponse> resetPassword(@RequestBody Map<String, String> resetData) {
//        String token = resetData.get("token");
//        String newPassword = resetData.get("newPassword");
//
//        if (token == null || newPassword == null) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Token and new password are required"));
//        }
//
//        userService.resetPassword(token, newPassword);
//        return ResponseEntity.ok(new MessageResponse("Password has been reset successfully"));
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<MessageResponse> handleRuntimeException(RuntimeException e) {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(new MessageResponse(e.getMessage()));
//    }
} 