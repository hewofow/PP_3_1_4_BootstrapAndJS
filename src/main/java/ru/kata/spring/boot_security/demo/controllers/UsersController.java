package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UsersController {
    @GetMapping("/admin")
    public String openAdminPanel() {
        return "users";
    }

    @GetMapping("/user")
    public String showProfile() {
        return "user";
    }
}