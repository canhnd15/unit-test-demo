package com.davidnguyen.unittestdemo.controller;

import com.davidnguyen.unittestdemo.dto.UserSdi;
import com.davidnguyen.unittestdemo.dto.UserSdo;
import com.davidnguyen.unittestdemo.entity.User;
import com.davidnguyen.unittestdemo.service.UserService;
import com.davidnguyen.unittestdemo.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("test get all users endpoint")
    public void call_get_all_user_should_return_users_without_condition() throws Exception {
        UserSdo user1 = UserSdo.builder()
                .id(1L)
                .username("user1")
                .email("user1@gmail.com")
                .password("user1.pwd")
                .build();
        UserSdo user2 = UserSdo.builder()
                .id(2L)
                .username("user2")
                .email("user2@gmail.com")
                .password("user2.pwd")
                .build();

        List<UserSdo> users = Arrays.asList(user1, user2);

        when(userService.getAllUser()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].email").value("user1@gmail.com"))
                .andExpect(jsonPath("$[0].password").value("user1.pwd"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].email").value("user2@gmail.com"))
                .andExpect(jsonPath("$[1].password").value("user2.pwd"));

    }

    @Test
    @DisplayName("test create user endpoint")
    public void call_create_user_should_return_created_user() throws Exception {
        UserSdi sdi = UserSdi.builder()
                .username("user")
                .email("user@gmail.com")
                .password("user.pwd")
                .build();

        UserSdo sdo = UserSdo.builder()
                .id(1L)
                .username("user")
                .email("user@gmail.com")
                .password("user.pwd")
                .build();

        when(userService.create(Mockito.any(sdi.getClass()))).thenReturn(sdo);

        mockMvc.perform(post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sdi)))
                .andExpect(status().isCreated())
                .andDo(result -> System.out.println("JSON result: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("user@gmail.com"))
                .andExpect(jsonPath("$.password").value("user.pwd"));
    }

    @Test
    @DisplayName("test batch insert user endpoint")
    public void call_create_batch_user_should_return_user_ids() throws Exception {
        UserSdi sdi1 = UserSdi.builder()
                .username("user1")
                .email("user1@gmail.com")
                .password("user1.pwd")
                .build();

        UserSdi sdi2 = UserSdi.builder()
                .username("user2")
                .email("user2@gmail.com")
                .password("user2.pwd")
                .build();

        List<UserSdi> sdi = Arrays.asList(sdi1, sdi2);

        List<Long> ids = Arrays.asList(1L, 2L);

        when(userService.batchInsertUser(Mockito.anyList())).thenReturn(ids);

        mockMvc.perform(post("/api/v1/users/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sdi)))
                .andExpect(status().isCreated())
                .andDo(result -> System.out.println("JSON result: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$[0]").value(1L))
                .andExpect(jsonPath("$[1]").value(2L));
    }

    @Test
    @DisplayName("test update user endpoint")
    public void call_update_user_should_return_updated_user_info() throws Exception {
        UserSdi sdi = UserSdi.builder()
                .username("user.updated")
                .email("user.updated@gmail.com")
                .password("user.updated.pwd")
                .build();

        UserSdo sdo = UserSdo.builder()
                .id(1L)
                .username("user.updated")
                .email("user.updated@gmail.com")
                .password("user.updated.pwd")
                .build();

        when(userService.update(Mockito.anyLong(), Mockito.any(UserSdi.class))).thenReturn(sdo);

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sdi)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user.updated"))
                .andExpect(jsonPath("$.email").value("user.updated@gmail.com"))
                .andExpect(jsonPath("$.password").value("user.updated.pwd"));

    }

    @Test
    @DisplayName("test delete user endpoint")
    public void call_delete_user_should_delete_user() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status()
                        .isNoContent());
    }
}
