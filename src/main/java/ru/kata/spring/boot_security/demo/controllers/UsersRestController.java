package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("api/users")
    public List<User> getAllUsers() {
        return userService.listUsers();
    }

    @GetMapping("/api/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable long id) {
        return userService.getById(id);
    }

    @GetMapping("api/roles")
    public List<Role> getAllRoles() {
        return roleService.listRoles();
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.remove(id);
    }

    @PutMapping("/api/users/{id}")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @PostMapping("/api/users")
    public void createUser(@RequestBody User user) {
        userService.add(user);
    }

    @GetMapping("/api/user")  // rename to api/users/principal or unite with getUser above
    @ResponseBody
    public User getPrincipal(Principal principal) {
        return userService.findByEmail(principal.getName());
    }
}
