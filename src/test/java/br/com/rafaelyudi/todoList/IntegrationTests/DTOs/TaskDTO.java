package br.com.rafaelyudi.todoList.IntegrationTests.DTOs;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class TaskDTO {

    private UUID id;
    private String description;
    private String title;
    private String priority;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private UUID idUser;
    private LocalDateTime createdAt;

    public TaskDTO() {
    }

    public TaskDTO(UUID id, String description, String title, String priority, LocalDateTime startAt, LocalDateTime endAt, UUID idUser, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.priority = priority;
        this.startAt = startAt;
        this.endAt = endAt;
        this.idUser = idUser;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDTO taskDTO = (TaskDTO) o;
        return Objects.equals(id, taskDTO.id) && Objects.equals(description, taskDTO.description) && Objects.equals(title, taskDTO.title) && Objects.equals(priority, taskDTO.priority) && Objects.equals(startAt, taskDTO.startAt) && Objects.equals(endAt, taskDTO.endAt) && Objects.equals(idUser, taskDTO.idUser) && Objects.equals(createdAt, taskDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, title, priority, startAt, endAt, idUser, createdAt);
    }
}
