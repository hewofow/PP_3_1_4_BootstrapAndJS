package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
    @GetMapping("/admin")
    public String openAdminPanel() {
        return "users";
    }

//    @GetMapping("/user")
//    public String showProfile(Model model, Principal principal) {
//        model.addAttribute("user", userService.findByEmail(principal.getName()));
//        return "user";
//    }
}