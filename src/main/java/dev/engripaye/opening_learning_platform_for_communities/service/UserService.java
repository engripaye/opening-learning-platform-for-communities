package dev.engripaye.opening_learning_platform_for_communities.service;

import dev.engripaye.opening_learning_platform_for_communities.model.Role;
import dev.engripaye.opening_learning_platform_for_communities.model.User;
import dev.engripaye.opening_learning_platform_for_communities.repository.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User upsertFromOidc(OidcUser oidcUser){
        String sub = oidcUser.getSubject();
        Optional<User> maybe = repository.findByOauthId(sub);
        User user = maybe.orElseGet(User::new);

        user.setOauthId(sub);
        user.setEmail(oidcUser.getEmail());
        user.setName(oidcUser.getFullName() != null ? oidcUser.getFullName() : oidcUser.getEmail());

        if (user.getRole() == null){
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_STUDENT);
            user.setRole(roles);
        }
        return repository.save(user);
    }

    public void promoteToTeacher(String userId) {
        repository.findById(userId).ifPresent(u -> {
            u.getRole().add(Role.ROLE_TEACHER);
            u.setVerifiedTeacher(true);
            repository.save(u);
        });
    }
}
