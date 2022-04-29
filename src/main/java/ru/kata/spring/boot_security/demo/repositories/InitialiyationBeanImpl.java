package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

@Component
public class InitialiyationBeanImpl implements InitializingBean {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public InitialiyationBeanImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /*
        log1 - p1
        log2 - p2
        ...
        log5 - p5
         */
        userRepository.save(new User("log1", "$2a$12$CRsBgI2Km.70/L.1F3kEvuFQP6RzcL9zDHsAl9UcqLhqpU6iGDUxe",
                "Vasya", "Ivanov", "1000000001"));
        userRepository.save(new User("log2", "$2a$12$vTtzBMiIAl9SadwvFpSIbOWCEtsceNMEwNGzSyW7FXziOgPnwkBje",
                "Petya", "Golovach", "2000000002"));
        userRepository.save(new User("log3", "$2a$12$1TR2x3Y650VdRe6ViLqD.ODMOOhyHghJl6NxRbMSd8RFWaHe2Y1l2",
                "Bob", "Sponge", "3000000003"));
        userRepository.save(new User("log4", "$2a$12$d6wUQqk75wylQHb.f3.7oesdT9Z7jefj0jO5UZXmzgt3c8h1TeftK",
                "Johan", "Kek", "4000000004"));
        userRepository.save(new User("log5", "$2a$12$wlbuP8huAQQXSd2XfbDUEeDrNKxE6cUp9EjWoWF6eYmMdCOjkJX7W",
                "Pepa", "Josefina", "5000000005"));
        userRepository.save(new User("admin", "$2a$12$nF5YQDpTYW/r3xlJViZCkuOsdtUKtZ7wprBuV0ey/U2CWyH7.d4m.",
                "Admin", "Admin", "6000000006"));

        roleRepository.save(new Role(1, "ROLE_USER"));
        roleRepository.save(new Role(2, "ROLE_ADMIN"));
    }
}
