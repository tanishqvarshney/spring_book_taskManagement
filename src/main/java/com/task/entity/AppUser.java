package com.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String mobileNumber;
    private String aadharNumber;
    private String selectedJurisdiction;
    private Boolean isOnboarded;
    private Long onboardedLevel;
    private LocalDate dob;
    private String profilePic;
    private Long languageType;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore

    public String getPassword() {
        return null;
    }

    @Override
    @JsonIgnore

    public String getUsername() {
        return mobileNumber;
    }

    @Override
    @JsonIgnore

    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isEnabled() {
        return true;
    }
}
