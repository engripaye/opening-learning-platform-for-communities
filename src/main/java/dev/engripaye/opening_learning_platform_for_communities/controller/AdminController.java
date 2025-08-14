package dev.engripaye.opening_learning_platform_for_communities.controller;

import dev.engripaye.opening_learning_platform_for_communities.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private  final UserService userService;

    public AdminController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/promote/{userId}")
    public void promoteToTeacher(@PathVariable String userId){
        userService.promoteToTeacher(userId);

    }
}
