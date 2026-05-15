package com.activityManager.user.service.impl;

import com.activityManager.user.entity.User;
import com.activityManager.user.entity.dto.UserLoginRequest;
import com.activityManager.user.entity.dto.UserRegisterRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.exception.UserAlreadyExistsException;
import com.activityManager.user.exception.UserAuthenticationException;
import com.activityManager.user.exception.UserNotFoundException;
import com.activityManager.user.repository.UserRepo;
import com.activityManager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(UserRegisterRequest request) {
        // Check if user already exists with this email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail(), "email");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(request.getEmail())
                .message("User Registered Successfully")
                .build();
    }

    @Override
    public UserResponse login(UserLoginRequest request) {
        try {
            // Verify user exists first
            if (userRepository.findByEmail(request.getEmail()).isEmpty()) {
                throw UserNotFoundException.forEmail(request.getEmail());
            }

            // Attempt authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Get the authenticated user to include ID in response
            User authenticatedUser = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

            return UserResponse.builder()
                    .id(authenticatedUser.getId())
                    .email(request.getEmail())
                    .message("Login SuccessFull")
                    .build();
                    
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new UserAuthenticationException(request.getEmail(), "Invalid email or password");
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UserAuthenticationException(request.getEmail(), "Authentication failed: " + e.getMessage());
        }
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return convertToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.forEmail(email));
        return convertToUserResponse(user);
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .message("User found successfully")
                .build();
    }
}
