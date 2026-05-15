package com.activityManager.user.entity.dto;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.dto.ActivityResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String message;
}
