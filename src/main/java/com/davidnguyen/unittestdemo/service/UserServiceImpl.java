package com.davidnguyen.unittestdemo.service;

import com.davidnguyen.unittestdemo.dto.UserSdi;
import com.davidnguyen.unittestdemo.dto.UserSdo;
import com.davidnguyen.unittestdemo.entity.User;
import com.davidnguyen.unittestdemo.exception.UserNotFoundException;
import com.davidnguyen.unittestdemo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserSdo> getAllUser() {
        return userRepository.findAll().stream().map(User::toSdo).toList();
    }

    @Override
    @Transactional
    public UserSdo create(UserSdi sdi) {
        User user = User.builder()
                .username(sdi.getUsername())
                .email(sdi.getEmail())
                .password(sdi.getPassword())
                .build();

        user = userRepository.save(user);

        return UserSdo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    @Transactional
    public List<Long> batchInsertUser(List<UserSdi> sdi) {
        List<User> users = sdi.stream().map(UserSdi::toEntity).toList();
        return userRepository.saveAllAndFlush(users).stream().map((User::getId)).toList();
    }

    @Override
    @Transactional
    public UserSdo update(Long id, UserSdi sdi) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        BeanUtils.copyProperties(sdi, updatedUser);
        userRepository.save(updatedUser);
        return User.toSdo(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(existUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSdo findExistUserById(Long id) {
        User existdUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return User.toSdo(existdUser);
    }
}
