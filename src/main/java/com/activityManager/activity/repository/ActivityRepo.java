package com.activityManager.activity.repository;

import com.activityManager.activity.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepo extends MongoRepository<Activity,String> {
    List<Activity> findByUserId(String userId);
}
