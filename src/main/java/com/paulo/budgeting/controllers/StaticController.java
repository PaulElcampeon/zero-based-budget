package com.paulo.budgeting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {

    @GetMapping("/")
    public String home() {
        return "/home.html";
    }

    @GetMapping("/create")
    public String create() {
        return "/create.html";
    }

    @GetMapping("/login")
    public String login() {
        return "/login.html";
    }

    @GetMapping("/work-sheet")
    public String app() {
        return "/zbb.html";
    }
}
