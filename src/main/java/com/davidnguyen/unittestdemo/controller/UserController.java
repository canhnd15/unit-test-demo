package com.davidnguyen.unittestdemo.controller;

import com.davidnguyen.unittestdemo.dto.UserSdi;
import com.davidnguyen.unittestdemo.dto.UserSdo;
import com.davidnguyen.unittestdemo.entity.User;
import com.davidnguyen.unittestdemo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ObjectMapper mapper;

    @GetMapping("/")
    public ResponseEntity<List<UserSdo>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserSdo> create(@RequestBody UserSdi sdi) {
        UserSdo sdo = userService.create(sdi);
        return new ResponseEntity<>(sdo, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Long>> batchInsert(@RequestBody List<UserSdi> sdi) {
        return new ResponseEntity<>(userService.batchInsertUser(sdi), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSdo> update(@PathVariable Long id, @RequestBody UserSdi sdi) {
        return new ResponseEntity<>(userService.update(id, sdi), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
