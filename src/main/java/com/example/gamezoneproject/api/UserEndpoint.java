package com.example.gamezoneproject.api;

import com.example.gamezoneproject.domain.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserEndpoint {
    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/name/availability")
    public ResponseEntity<Boolean> checkUserLoginAvailability(@RequestParam String name) {
        boolean loginAvailable = userService.isLoginAvailable(name);
        return ResponseEntity.ok(loginAvailable);
    }
    @GetMapping("/email/availability")
    public ResponseEntity<Boolean> checkUserEmailAvailability(@RequestParam String name) {
        boolean emailAvailable = userService.isEmailAvailable(name);
        return ResponseEntity.ok(emailAvailable);

    }
}
