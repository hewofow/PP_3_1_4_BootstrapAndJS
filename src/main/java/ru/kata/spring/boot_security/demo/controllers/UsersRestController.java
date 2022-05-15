package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class UsersRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/odmen")
    public List<User> getAllUsers() {
        return userService.listUsers();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.listRoles();
    }

    @GetMapping("/uiser")
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user";
    }

    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
    }

    @PutMapping("/admin/users/{id}")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @PostMapping("/admin/users")
    public void createUser(@RequestBody User user) {
        userService.add(user);
    }

    @GetMapping("/user")
    @ResponseBody
    public User getPrincipal(Principal principal) {
        return userService.findByEmail(principal.getName());
    }
}
