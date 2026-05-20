package com.activityManager.user.mapper;

import com.activityManager.user.entity.User;
import com.activityManager.user.entity.dto.UserResponse;

public class UserMapper {

    public static UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
