package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(Role role) { roleRepository.save(role); }

    @Override
    public void remove(long id) { roleRepository.deleteById(id); }

    @Override
    public void update(Role role) { roleRepository.save(role); }

    @Override
    public Role getById(long id) { return roleRepository.findById(id); }

    @Override
    public List<Role> listRoles() {return roleRepository.findAll(); }
}
