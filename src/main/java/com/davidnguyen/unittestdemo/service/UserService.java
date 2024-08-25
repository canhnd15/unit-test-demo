package com.davidnguyen.unittestdemo.service;

import com.davidnguyen.unittestdemo.dto.UserSdi;
import com.davidnguyen.unittestdemo.dto.UserSdo;

import java.util.List;

public interface UserService {
    List<UserSdo> getAllUser();

    UserSdo create(UserSdi sdi);

    List<Long> batchInsertUser(List<UserSdi> sdi);

    UserSdo update(Long id, UserSdi sdi);

    void delete(Long id);

    UserSdo findExistUserById(Long id) throws Exception;
}
