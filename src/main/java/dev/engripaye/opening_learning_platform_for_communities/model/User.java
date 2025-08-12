package dev.engripaye.opening_learning_platform_for_communities.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("users")
public class User {
    @Id private String id;
    private String oauthId;
    private String email;
    private String name;
    private Set<Role> role;
    private boolean verifiedTeacher = false;


    // No-args constructor
    public User() {
    }


    public User(String id, String oauthId, String email, String name, Set<Role> role, boolean verifiedTeacher) {
        this.id = id;
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.verifiedTeacher = verifiedTeacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public boolean isVerifiedTeacher() {
        return verifiedTeacher;
    }

    public void setVerifiedTeacher(boolean verifiedTeacher) {
        this.verifiedTeacher = verifiedTeacher;
    }
}
