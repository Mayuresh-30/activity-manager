package com.activityManager.user.service.impl;

import com.activityManager.user.entity.User;
import com.activityManager.user.entity.dto.AuthResponse;
import com.activityManager.user.entity.dto.UserLoginRequest;
import com.activityManager.user.entity.dto.UserRegisterRequest;
import com.activityManager.user.entity.dto.UserResponse;
import com.activityManager.user.entity.dto.UpdateUserRequest;
import com.activityManager.user.exception.UserAlreadyExistsException;
import com.activityManager.user.exception.UserAuthenticationException;
import com.activityManager.user.exception.UserNotFoundException;
import com.activityManager.user.repository.UserRepo;
import com.activityManager.user.security.JwtTokenProvider;
import com.activityManager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponse register(UserRegisterRequest request) {
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
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("User registered successfully")
                .build();
    }

    @Override
    public AuthResponse login(UserLoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> UserNotFoundException.forEmail(request.getEmail()));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateToken(user);
            return AuthResponse.builder()
                    .accessToken(token)
                    .tokenType("Bearer")
                    .userId(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole().name())
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

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (userRepository.findByEmail(request.getEmail()).isPresent() &&
                    !userRepository.findByEmail(request.getEmail()).get().getId().equals(id)) {
                throw new UserAlreadyExistsException(request.getEmail(), "email");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("User updated successfully")
                .build();
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("User found successfully")
                .build();
    }
}
