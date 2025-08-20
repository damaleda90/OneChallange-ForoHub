package com.danielara.ForoHub.models.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicDetailsDTO(
        @NotBlank Long id,
        @NotBlank String title,
        @NotBlank String message,
        @NotBlank LocalDateTime creationDate,
        @NotBlank String courseName,
        @NotNull TopicStatus status,
        @NotBlank String authorName
) {
    public TopicDetailsDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getCourse().getName(),
                topic.getStatus(),
                topic.getUser().getName()
        );
    }
}
