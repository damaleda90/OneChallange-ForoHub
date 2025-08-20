package com.danielara.ForoHub.models.topic;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicCancelamientoDTO(
        @NotNull Long id,
        @NotNull String title,
        @NotNull String message,
        @NotNull LocalDateTime creationDate) {
}
