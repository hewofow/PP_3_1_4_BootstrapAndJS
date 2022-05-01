package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class UsersController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String showTable(Model model) {
        model.addAttribute("userList", userService.listUsers());
        return "users";
    }

    @GetMapping("/user")
    public String userProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findByLogin(principal.getName()));
        return "user";
    }

    @GetMapping("/admin/users/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roleList", roleService.listRoles());
        return "new";
    }

    @PostMapping("/admin/users")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        User user = userService.getById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.listRoles());
        return "edit";
    }

    @PatchMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
        return "redirect:/admin/users";
    }

}