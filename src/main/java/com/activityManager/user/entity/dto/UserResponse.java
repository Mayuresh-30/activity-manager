package com.activityManager.user.entity.dto;

import com.activityManager.activity.entity.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private String email;
//    private List<Activity> activities;
    private String message;
}
