package com.paulo.budgeting.controllers;


import com.paulo.budgeting.config.JwtUtil;
import com.paulo.budgeting.domain.Budget;
import com.paulo.budgeting.dto.AuthRequest;
import com.paulo.budgeting.dto.AuthResponse;
import com.paulo.budgeting.dto.BudgetDto;
import com.paulo.budgeting.service.BudgetService;
import com.paulo.budgeting.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final BudgetService budgetService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails user = userDetailsService.loadUserByUsername(authRequest.getEmail());

        if (user != null) {
            List<BudgetDto> budgets = budgetService.findBudgetsByEmail(authRequest.getEmail()).stream().map(Budget::mapToDto).collect(Collectors.toList());
            return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user), budgets));
        }

        return ResponseEntity.status(400).body(Optional.empty());
    }
}
