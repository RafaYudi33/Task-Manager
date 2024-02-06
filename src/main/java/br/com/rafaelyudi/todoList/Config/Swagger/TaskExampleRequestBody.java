package br.com.rafaelyudi.todoList.Config.Swagger;

import java.time.LocalDateTime;

public record TaskExampleRequestBody(
        String description,
        String title,
        String priority,
        LocalDateTime startAt,
        LocalDateTime endAt,
        LocalDateTime createdAt
) {
}
