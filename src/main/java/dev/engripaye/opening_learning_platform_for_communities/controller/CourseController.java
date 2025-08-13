package dev.engripaye.opening_learning_platform_for_communities.controller;

import dev.engripaye.opening_learning_platform_for_communities.model.Course;
import dev.engripaye.opening_learning_platform_for_communities.model.Role;
import dev.engripaye.opening_learning_platform_for_communities.service.CourseService;
import dev.engripaye.opening_learning_platform_for_communities.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;


    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/public")
    public List<Course> listPublic(){
        return courseService.listPublic();
    }

    @PostMapping
    public Course create(@AuthenticationPrincipal OidcUser oidcUser, @RequestBody Course payload){
        var user = userService.upsertFromOidc(oidcUser);
        payload.setCreatedByUserId(user.getId());
        return courseService.create(payload);
    }

    @GetMapping("/{id}")
    public Course get(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable String id){
        var c = courseService.get(id).orElseThrow(() -> new RuntimeException("Not Found"));

        // RESTRICTED CONTENT CHECK
        if(c.getVisibility() == Course.Visibility.RESTRICTED) {
            boolean hasTeacher = oidcUser.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals(Role.ROLE_TEACHER.name()) || a.getAuthority().equals(Role.ROLE_ADMIN.name()));

            if(!hasTeacher) throw new RuntimeException("Access denied");
        }
        return c;
    }
}
