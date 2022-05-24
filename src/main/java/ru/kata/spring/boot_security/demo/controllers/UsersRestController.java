package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UsersRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.listRoles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("users/{id}")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("users")
    public void createUser(@RequestBody User user) {
        userService.add(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("user")
    public ResponseEntity<User> getPrincipal(Principal principal) {
        return ResponseEntity.ok(userService.findByEmail(principal.getName()));
    }
}
