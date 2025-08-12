package dev.engripaye.opening_learning_platform_for_communities.service;

import dev.engripaye.opening_learning_platform_for_communities.model.Course;
import dev.engripaye.opening_learning_platform_for_communities.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    public final CourseRepository repository;


    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course create(Course course) {
        return repository.save(course);
    }

    public List<Course> listPublic() {
        return repository.findByVisibility(Course.Visibility.PUBLIC);
    }

    public List<Course> listByCreator(String userId) {
        return repository.findByCreatedByUserId(userId);
    }

    public Optional<Course> get(String id) {
        return repository.findById(id);
    }
}
