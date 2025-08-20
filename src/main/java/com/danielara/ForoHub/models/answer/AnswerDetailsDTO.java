package com.danielara.ForoHub.models.answer;

import jakarta.validation.constraints.NotBlank;

public record AnswerDetailsDTO(
        @NotBlank Long id,
        @NotBlank String mensaje,
        @NotBlank String autor,
        @NotBlank String topico
) {
    public AnswerDetailsDTO(Answer answer) {
        this(
                answer.getId(),
                answer.getMessage(),
                answer.getAuthor().getName(),
                answer.getTopic().getTitle()
        );
    }
}

