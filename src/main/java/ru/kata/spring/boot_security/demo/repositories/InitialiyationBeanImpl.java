package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.SecureRandom;

@Component
public class InitialiyationBeanImpl implements InitializingBean {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public InitialiyationBeanImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Role roleAdmin = new Role(1, "ROLE_ADMIN");
        Role roleUser = new Role(2, "ROLE_USER");
        roleService.add(roleAdmin);
        roleService.add(roleUser);
        roleService.add(new Role(3, "ROLE_BOBA"));

        User user1 = new User("usr1@mail.ru", "1", "Vasya", "Ivanov", roleUser);
        User user2 = new User("usr2@mail.ru", "2", "Petya", "Golovach", roleUser);
        User user3 = new User("usr3@mail.ru", "3", "Bob", "Sponge", roleUser);
        User user4 = new User("usr4@mail.ru", "4", "Johan", "Kek", roleUser);
        User user5 = new User("usr5@mail.ru", "5", "Pepa", "Josefina", roleUser, roleAdmin);
        User user6 = new User("admin@mail.ru", "admin", "Admin", "Admin", roleAdmin);
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);
        userService.add(user5);
        userService.add(user6);
    }
}
