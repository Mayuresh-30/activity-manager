package com.activityManager.activity.service.impl;

import com.activityManager.activity.entity.Activity;
import com.activityManager.activity.entity.ActivityPriority;
import com.activityManager.activity.entity.dto.ActivityRequest;
import com.activityManager.activity.entity.dto.ActivityResponse;
import com.activityManager.activity.exception.ActivityAccessDeniedException;
import com.activityManager.activity.exception.ActivityNotFoundException;
import com.activityManager.activity.exception.ActivityStatusException;
import com.activityManager.activity.mapper.ActivityMapper;
import com.activityManager.activity.repository.ActivityRepo;
import com.activityManager.activity.service.ActivityService;
import com.activityManager.user.entity.User;
import com.activityManager.user.exception.UserNotFoundException;
import com.activityManager.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepo repo;
    private final UserRepo userRepo;

    @Override
    public ActivityResponse create(String userId, ActivityRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Activity activity = Activity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .userId(user.getId())
                .status(Activity.ActivityStatus.PENDING)
                .projectId(request.getProjectId())
                .priority(request.getPriority() != null ? request.getPriority() : ActivityPriority.MEDIUM)
                .dueDate(request.getDueDate())
                .tags(request.getTags())
                .build();

        Activity saved = repo.save(activity);
        return ActivityMapper.toResponse(saved);
    }

    @Override
    public Page<ActivityResponse> getUserActivities(String userId, Activity.ActivityStatus status, Pageable pageable) {
        userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Page<Activity> activities = status == null
                ? repo.findByUserId(userId, pageable)
                : repo.findByUserIdAndStatus(userId, status, pageable);

        return activities.map(ActivityMapper::toResponse);
    }

    @Override
    public ActivityResponse getActivityById(String userId, String activityId) {
        Activity activity = loadOwnedActivity(activityId, userId);
        return ActivityMapper.toResponse(activity);
    }

    @Override
    public ActivityResponse startActivity(String userId, String activityId) {
        Activity activity = loadOwnedActivity(activityId, userId);

        if (activity.getStatus() != Activity.ActivityStatus.PENDING) {
            throw new ActivityStatusException(activityId, activity.getStatus(), Activity.ActivityStatus.IN_PROGRESS,
                    "Only pending activities can be started");
        }

        activity.setStatus(Activity.ActivityStatus.IN_PROGRESS);
        return ActivityMapper.toResponse(repo.save(activity));
    }

    @Override
    public ActivityResponse completeActivity(String userId, String activityId) {
        Activity activity = loadOwnedActivity(activityId, userId);

        if (activity.getStatus() != Activity.ActivityStatus.IN_PROGRESS) {
            throw new ActivityStatusException(activityId, activity.getStatus(), Activity.ActivityStatus.COMPLETED,
                    "Only in-progress activities can be completed");
        }

        activity.setStatus(Activity.ActivityStatus.COMPLETED);
        return ActivityMapper.toResponse(repo.save(activity));
    }

    @Override
    public ActivityResponse cancelActivity(String userId, String activityId) {
        Activity activity = loadOwnedActivity(activityId, userId);

        if (activity.getStatus() == Activity.ActivityStatus.COMPLETED) {
            throw new ActivityStatusException(activityId, activity.getStatus(), Activity.ActivityStatus.CANCELLED,
                    "Completed activities cannot be cancelled");
        }

        if (activity.getStatus() == Activity.ActivityStatus.CANCELLED) {
            throw new ActivityStatusException(activityId, activity.getStatus(), Activity.ActivityStatus.CANCELLED,
                    "Activity is already cancelled");
        }

        activity.setStatus(Activity.ActivityStatus.CANCELLED);
        return ActivityMapper.toResponse(repo.save(activity));
    }

    @Override
    public ActivityResponse updateActivity(String userId, String activityId, ActivityRequest request) {
        Activity activity = loadOwnedActivity(activityId, userId);

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            activity.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            activity.setDescription(request.getDescription());
        }
        if (request.getProjectId() != null) {
            activity.setProjectId(request.getProjectId());
        }
        if (request.getPriority() != null) {
            activity.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            activity.setDueDate(request.getDueDate());
        }
        if (request.getTags() != null) {
            activity.setTags(request.getTags());
        }

        return ActivityMapper.toResponse(repo.save(activity));
    }

    @Override
    public void deleteActivity(String userId, String activityId) {
        Activity activity = loadOwnedActivity(activityId, userId);
        repo.delete(activity);
    }

    private Activity loadOwnedActivity(String activityId, String userId) {
        Activity activity = repo.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException(activityId));

        if (!activity.getUserId().equals(userId)) {
            throw new ActivityAccessDeniedException(activityId, userId);
        }
        return activity;
    }
}
