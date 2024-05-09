package br.com.rafaelyudi.todoList.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCredentialsDTO(@NotNull String username, @NotNull String password) {
}
