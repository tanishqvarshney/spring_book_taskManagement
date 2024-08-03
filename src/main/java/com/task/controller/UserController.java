package com.task.controller;

import com.task.entity.AppUser;
import com.task.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/sendOTP")
    public ResponseEntity<Object> sendOTP(@RequestBody AppUser mobileNumber) {
        return userService.sendOTP(mobileNumber.getMobileNumber());
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Object> verifyOTP(@RequestBody Map<String, String> json) {
        return userService.verifyOTP(json.get("mobileNumber"), json.get("otp"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAppUserByMobileNumber(@PathVariable String id) {
        return userService.getAppUserByMobileNumber(id);
    }

    @PatchMapping("/{mobileNumber}")
    public ResponseEntity<Object> editUserDetails(@RequestBody AppUser user, @PathVariable String mobileNumber) {
        user.setMobileNumber(mobileNumber);
        return userService.editUserDetails(user);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

}
