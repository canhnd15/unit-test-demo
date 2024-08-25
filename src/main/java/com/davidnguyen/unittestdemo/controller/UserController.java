package com.davidnguyen.unittestdemo.controller;

import com.davidnguyen.unittestdemo.dto.UserSdi;
import com.davidnguyen.unittestdemo.dto.UserSdo;
import com.davidnguyen.unittestdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserSdo>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/")
    public ResponseEntity<UserSdo> create(@RequestBody UserSdi sdi) {
        return ResponseEntity.ok(userService.create(sdi));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Long>> batchInsert(@RequestBody List<UserSdi> sdi) {
        return ResponseEntity.ok(userService.batchInsertUser(sdi));
    }

    @PutMapping("/")
    public ResponseEntity<UserSdo> update(@RequestParam Long id, @RequestBody UserSdi sdi) throws Exception {
        return ResponseEntity.ok(userService.update(id, sdi));
    }

    @DeleteMapping("/")
    public void delete(@RequestParam Long id) {
        userService.delete(id);
    }
}
