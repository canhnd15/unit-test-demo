package com.davidnguyen.unittestdemo.entity;

import com.davidnguyen.unittestdemo.dto.UserSdo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    public static UserSdo toSdo(User user) {
        UserSdo sdo = new UserSdo();
        BeanUtils.copyProperties(user, sdo);

        return sdo;
    }
}
