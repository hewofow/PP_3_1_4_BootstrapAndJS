package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("api/users")
    public List<User> getAllUsers() {
        return userService.listUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("api/roles")
    public List<Role> getAllRoles() {
        return roleService.listRoles();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/users/{id}")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/users")
    public void createUser(@RequestBody User user) {
        userService.add(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/api/user")
    @ResponseBody
    public User getPrincipal(Principal principal) {
        return userService.findByEmail(principal.getName());
    }
}
