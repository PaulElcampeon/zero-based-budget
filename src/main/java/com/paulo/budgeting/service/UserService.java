package com.paulo.budgeting.service;


import com.paulo.budgeting.domain.User;
import com.paulo.budgeting.domain.enums.UserRoles;
import com.paulo.budgeting.dto.CreateUserRequest;
import com.paulo.budgeting.dto.UpdateUserRequest;
import com.paulo.budgeting.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    public User addUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(getEncodedPassword(createUserRequest.getPassword()));
        user.setRole(UserRoles.USER);
        return repo.save(user);
    }

    public void updateUser(UpdateUserRequest request) {
        findUserByEmail(request.getEmail())
                .ifPresent(user -> repo.save(user));
    }

    public Optional<User> findUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    public void deleteUser(String email) {
        repo.deleteByEmail(email);
    }

    public List<User> findAllUsers() {
        return repo.findAll();
    }

    public String getEncodedPassword(String oldPassword) {
        return passwordEncoder.encode(oldPassword);
    }
}
