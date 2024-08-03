package com.task.service;

import com.task.config.AppConfig;
import com.task.config.AppExtensions;
import com.task.entity.AppUser;
import com.task.entity.ReturnResponse;
import com.task.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public ResponseEntity<Object> sendOTP(String mobileNumber) {
        if (mobileNumber.isEmpty() || !AppExtensions.isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnResponse.message("Invalid mobile number"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ReturnResponse.data(String.format("Successfully sent OTP to %s", mobileNumber)));
    }


    public ResponseEntity<Object> verifyOTP(String mobileNumber, String otp) {
        if (otp == null || otp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnResponse.message("Invalid OTP "));
        }
        if (mobileNumber == null || mobileNumber.isEmpty() || !AppExtensions.isValidMobileNumber(mobileNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnResponse.message("Invalid mobile number"));
        }
        if (otp.equals(AppConfig.APP_OTP)) {
            Optional<AppUser> user = userRepository.findByMobileNumber(mobileNumber);
            if (user.isPresent() && user.get().getId() != null) {
                return getJWTToken(user.get());
            } else {
                AppUser appUser = new AppUser();
                appUser.setMobileNumber(mobileNumber);
                userRepository.save(appUser);
                return getJWTToken(appUser);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnResponse.message("Invalid OTP "));
        }
    }

    private ResponseEntity<Object> getJWTToken(AppUser appUser2) {
        String jwtToken = jwtService.generateToken(appUser2);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user", appUser2);
        map.put("token", jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(ReturnResponse.data(map));
    }


    public ResponseEntity<Object> getAppUserByMobileNumber(String mobileNumber) {
        Optional<AppUser> user = userRepository.findByMobileNumber(mobileNumber);
        return user.<ResponseEntity<Object>>map(
                appUser -> ResponseEntity.status(HttpStatus.OK).body(ReturnResponse.data(appUser)))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ReturnResponse.message("User Does not Exist")));
    }


    public ResponseEntity<Object> editUserDetails(AppUser user) {
        Optional<AppUser> appUser = userRepository.findByMobileNumber(user.getMobileNumber());
        if (appUser.isPresent()) {
            AppUser user1 = appUser.get();
            user1.setName(user.getName());
            user1.setAadharNumber(user.getAadharNumber());
            user1.setSelectedJurisdiction(user.getSelectedJurisdiction());
            user1.setOnboardedLevel(user.getOnboardedLevel());
            user1.setIsOnboarded(user.getIsOnboarded());
            user1.setDob(user.getDob());
            user1.setProfilePic(user.getProfilePic());
            user1.setLanguageType(user.getLanguageType());

            userRepository.save(user1);
            return ResponseEntity.status(HttpStatus.OK).body(ReturnResponse.data(user1));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnResponse.message("User Does not Exist"));
        }
    }

    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(ReturnResponse.data(userRepository.findAll()));
    }
}
