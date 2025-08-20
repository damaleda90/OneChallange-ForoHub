package com.danielara.ForoHub.models.user;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationData(@NotBlank String login, @NotBlank  String password) {
}
