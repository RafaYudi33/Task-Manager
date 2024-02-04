package br.com.rafaelyudi.todoList.Config.Swagger;

import java.time.LocalDateTime;
import java.util.UUID;

public record taskExampleForSwagger(
        String description,
        String title,
        String priority,
        LocalDateTime startAt,
        LocalDateTime endAt,
        LocalDateTime createdAt
) {
}
