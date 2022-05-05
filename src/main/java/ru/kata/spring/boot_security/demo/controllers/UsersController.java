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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UsersController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String showTable(Model model, Principal principal) {
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.listRoles());
        return "users";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
        return "redirect:/admin";
    }

}