package com.paulo.budgeting.controllers;

import com.paulo.budgeting.domain.User;
import com.paulo.budgeting.dto.CreateUserRequest;
import com.paulo.budgeting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.addUser(request);
        return ResponseEntity.ok(user);
    }
}
