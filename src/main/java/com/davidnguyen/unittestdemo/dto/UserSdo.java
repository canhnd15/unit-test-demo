package com.davidnguyen.unittestdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSdo {
    private Long id;
    private String username;
    private String email;
    private String password;
}
