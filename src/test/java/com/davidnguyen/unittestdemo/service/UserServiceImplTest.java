package com.davidnguyen.unittestdemo.service;

import com.davidnguyen.unittestdemo.dto.UserSdi;
import com.davidnguyen.unittestdemo.dto.UserSdo;
import com.davidnguyen.unittestdemo.entity.User;
import com.davidnguyen.unittestdemo.exception.UserNotFoundException;
import com.davidnguyen.unittestdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void should_return_list_of_all_users() {
        User user1 = User.builder()
                .id(1L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .build();

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserSdo> listOfUser = userService.getAllUser();

        assertNotNull(listOfUser);
        assertEquals(2, listOfUser.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void when_create_new_user_should_return_created_user() {
        UserSdi sdi = UserSdi.builder()
                .username("user")
                .email("user@gmail.com")
                .password("user.pwd")
                .build();

        User user = User.builder()
                .username(sdi.getUsername())
                .email(sdi.getEmail())
                .password(sdi.getPassword())
                .build();

        when(userRepository.save(user)).thenReturn(user);

        UserSdo createdUser = userService.create(sdi);

        verify(userRepository, times(1)).save(any());

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
    }

    @Test
    void when_batch_create_user_should_return_created_users() {
        // Given
        UserSdi user1 = UserSdi.builder()
                .username("user1")
                .email("user1@gmail.com")
                .password("user1.pwd")
                .build();

        UserSdi user2 = UserSdi.builder()
                .username("user2")
                .email("user2@gmail.com")
                .password("user2.pwd")
                .build();

        List<UserSdi> sdi = Arrays.asList(user1, user2);

        List<User> users = sdi.stream().map(UserSdi::toEntity).toList();

        when(userRepository.saveAllAndFlush(users)).thenReturn(users);

        List<Long> createdId = userService.batchInsertUser(sdi);

        assertEquals(createdId.size(), sdi.size());
    }

    @Test
    void when_update_user_should_return_updated_user() {
        UserSdi sdi = UserSdi.builder()
                .username("username_update")
                .email("user_update@gmail.com")
                .password("pwd_update")
                .build();

        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username(sdi.getUsername())
                .email(sdi.getEmail())
                .password(sdi.getPassword())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserSdo updatedUser = userService.update(userId, sdi);

        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
    }

    @Test
    void should_throw_exception_when_updated_on_invalid_id() {
        UserSdi sdi = UserSdi.builder()
                .username("username_update")
                .email("user_update@gmail.com")
                .password("pwd_update")
                .build();

        Long invalidUserId = -1L;

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(invalidUserId, sdi));
    }

    @Test
    void should_delete_user_when_id_is_valid() {
        Long userId = 1L;

        User validUser = User.builder()
                .id(userId)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(validUser));

        userService.delete(userId);

        verify(userRepository, times(1)).delete(validUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void should_not_delete_user_on_invalid_id() {
        Long invalidUserId = -1L;
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.delete(invalidUserId));
    }

    @Test
    void give_an_valid_id_should_return_user_with_that_id() {
        Long validUserId = 1L;
        User validUser = User.builder()
                .id(validUserId)
                .build();

        when(userRepository.findById(validUserId)).thenReturn(Optional.of(validUser));

        UserSdo existUser = userService.findExistUserById(validUserId);

        assertEquals(existUser.getId(), validUser.getId());
        verify(userRepository, times(1)).findById(validUserId);
    }

    @Test
    void give_an_invalid_id_should_throw_exception_when_find_user_by_id() {
        Long invalidUserId = -1L;
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findExistUserById(invalidUserId));
    }
}