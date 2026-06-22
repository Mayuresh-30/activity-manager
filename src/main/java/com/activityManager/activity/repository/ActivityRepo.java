package com.activityManager.activity.repository;

import com.activityManager.activity.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepo extends MongoRepository<Activity,String> {
    List<Activity> findByUserId(String userId);
    Page<Activity> findByUserId(String userId, Pageable pageable);
    Page<Activity> findByUserIdAndStatus(String userId, Activity.ActivityStatus status, Pageable pageable);
}
