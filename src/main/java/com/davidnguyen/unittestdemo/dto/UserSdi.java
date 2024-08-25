package com.davidnguyen.unittestdemo.dto;

import com.davidnguyen.unittestdemo.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserSdi {
    private String username;
    private String email;
    private String password;

    public static User toEntity(UserSdi sdi) {
        return User.builder()
                .username(sdi.getUsername())
                .email(sdi.getEmail())
                .password(sdi.getPassword())
                .build();
    }
}
