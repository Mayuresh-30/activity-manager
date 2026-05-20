package com.activityManager.user.service.impl;

import com.activityManager.user.entity.User;
import com.activityManager.user.entity.UserRole;
import com.activityManager.user.entity.dto.CreateAdminRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.exception.UserAlreadyExistsException;
import com.activityManager.user.mapper.UserMapper;
import com.activityManager.user.repository.UserRepo;
import com.activityManager.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createAdmin(CreateAdminRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail(), "email");
        }

        User admin = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_ADMIN)
                .build();

        userRepository.save(admin);

        return UserResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .role(admin.getRole().name())
                .message("Admin user created successfully")
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> listOfUsersEntity = userRepository.findAll();
        return listOfUsersEntity.stream().map(UserMapper::toUserResponse).toList();

    }
}
