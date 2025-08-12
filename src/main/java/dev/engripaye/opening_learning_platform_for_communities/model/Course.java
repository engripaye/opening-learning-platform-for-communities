package dev.engripaye.opening_learning_platform_for_communities.model;

import org.springframework.aot.generate.AccessControl;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("courses")
public class Course {

    @Id private String id;
    private String title;
    private String description;
    private String content;
    private Visibility visibility = Visibility.PUBLIC;
    private String createdByUserId;
    private Instant createdAt = Instant.now();

    // no args
    public Course(){

    }
    public Course(String id, String title, String description, String content, Visibility visibility, String createdByUserId, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.visibility = visibility;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
