package com.paulo.budgeting.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;
    private String password;

}
