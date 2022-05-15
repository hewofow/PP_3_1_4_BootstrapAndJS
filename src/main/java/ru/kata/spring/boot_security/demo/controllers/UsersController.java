package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class UsersController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showTable(Model model, Principal principal) {
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.listRoles());
        return "users";
    }

//    @GetMapping("/user")
//    public String showProfile(Model model, Principal principal) {
//        model.addAttribute("user", userService.findByEmail(principal.getName()));
//        return "user";
//    }
}