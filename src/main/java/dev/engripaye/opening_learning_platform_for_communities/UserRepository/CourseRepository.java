package dev.engripaye.opening_learning_platform_for_communities.UserRepository;

import dev.engripaye.opening_learning_platform_for_communities.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findByVisibility(Course.Visibility v);
    List<Course> findByCreatedByUserId(String userId);
}
