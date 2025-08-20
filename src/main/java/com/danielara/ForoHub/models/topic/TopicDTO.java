package com.danielara.ForoHub.models.topic;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record TopicDTO(
       @NotBlank Long id,
       @NotBlank String title,
       @NotBlank String message,
       @NotBlank LocalDateTime creationDate) {
    public TopicDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate());
    }

    public static Page<TopicDTO> convert(Page<Topic> topics) {
        return topics.map(TopicDTO::new);
    }
}
