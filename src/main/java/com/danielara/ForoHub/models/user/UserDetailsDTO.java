package com.danielara.ForoHub.models.user;

import jakarta.validation.constraints.NotBlank;

public record UserDetailsDTO(
        @NotBlank Long id,
        @NotBlank String name,
        @NotBlank String login
) {
    public UserDetailsDTO(User user) {
        this(user.getId(), user.getName(), user.getLogin());
    }
}