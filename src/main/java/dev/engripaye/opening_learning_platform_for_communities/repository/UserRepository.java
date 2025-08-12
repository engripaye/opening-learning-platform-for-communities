package dev.engripaye.opening_learning_platform_for_communities.repository;

import dev.engripaye.opening_learning_platform_for_communities.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByOauthId(String oauthId);
    Optional<User> findByEmail(String email);
}
