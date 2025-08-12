package dev.engripaye.opening_learning_platform_for_communities.UserRepository;

import dev.engripaye.opening_learning_platform_for_communities.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface repository extends MongoRepository<User, String> {

    Optional<User> findByOauthId(String oauthId);
    Optional<User> findByEmail(String email);
}
