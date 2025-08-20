package com.danielara.ForoHub.models.answer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AnswerDTO {

    @NotBlank private Long id;
    @NotBlank private String message;
    @NotBlank private LocalDateTime creationDate;
    @NotBlank private String authorName;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.message = answer.getMessage();
        this.creationDate = answer.getCreatedAt();
        this.authorName = answer.getAuthor().getName();
    }
}
