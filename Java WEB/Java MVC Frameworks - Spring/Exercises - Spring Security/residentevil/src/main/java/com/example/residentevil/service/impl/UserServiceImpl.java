package com.example.residentevil.service.impl;

import com.example.residentevil.domain.entities.Role;
import com.example.residentevil.domain.entities.User;
import com.example.residentevil.domain.models.service.UserServiceModel;
import com.example.residentevil.repository.UserRepository;
import com.example.residentevil.repository.UserRoleRepository;
import com.example.residentevil.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    private static final String ROOT_ADMIN = "ROOT_ADMIN";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String DEFAULT_USER_NOT_FOUND_EX_MSG = "Username not found.";
    private static final String DEFAULT_NOT_AUTHORIZE_EX_MSG = "You are not authorize to update ROOT_ADMIN role.";

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails result = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(DEFAULT_USER_NOT_FOUND_EX_MSG));
        return result;
    }

    @Override
    public void register(UserServiceModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);

        String encodedPassword = bCryptPasswordEncoder.encode(userServiceModel.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setAuthorities(getRolesForRegistration());

        this.userRepository.saveAndFlush(userEntity);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(uEntity -> this.modelMapper.map(uEntity, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User userEntity = this.userRepository.findByUsername(username).orElse(null);

        return userEntity == null ? null
                : this.modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findById(String id) {
        User userEntity = this.userRepository.findById(id).orElse(null);

        return userEntity == null ? null
                : this.modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public void updateRole(String id, String role) {
        User userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(DEFAULT_USER_NOT_FOUND_EX_MSG));

        boolean isRootAdmin = userEntity.getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList())
                .contains(ROOT_ADMIN);
        if (isRootAdmin) {
            throw new IllegalArgumentException(DEFAULT_NOT_AUTHORIZE_EX_MSG);
        }

        updateUserRole(userEntity, role);
        this.userRepository.saveAndFlush(userEntity);
    }

    private void updateUserRole(User userEntity, String role) {
        Set<Role> newRole = new HashSet<>();
        switch (role) {
            case ROLE_USER:
                newRole.add(this.userRoleRepository.findByAuthority(ROLE_USER));
                break;
            case ROLE_MODERATOR:
                newRole.add(this.userRoleRepository.findByAuthority(ROLE_MODERATOR));
                newRole.add(this.userRoleRepository.findByAuthority(ROLE_USER));
                break;
            case ROLE_ADMIN:
                newRole.add(this.userRoleRepository.findByAuthority(ROLE_ADMIN));
                newRole.add(this.userRoleRepository.findByAuthority(ROLE_MODERATOR));
                newRole.add(this.userRoleRepository.findByAuthority(ROLE_USER));
                break;
        }
        userEntity.setAuthorities(newRole);
    }

    private Set<Role> getRolesForRegistration() {
        Set<Role> roles = new HashSet<>();

        if(this.userRepository.count() == 0) {
            roles.add(this.userRoleRepository.findByAuthority(ROOT_ADMIN));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_ADMIN));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_ADMIN));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_MODERATOR));
            roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
        } else {
            roles.add(this.userRoleRepository.findByAuthority(ROLE_USER));
        }

        return roles;
    }
}
