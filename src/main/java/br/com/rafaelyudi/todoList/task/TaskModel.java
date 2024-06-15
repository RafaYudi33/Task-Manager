package br.com.rafaelyudi.todoList.Task;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.rafaelyudi.todoList.User.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;


@Entity(name = "tb_tasks" )

public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id; 
    private String description; 

    private String title; 
    private String priority; 
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @NotBlank
    @Column(nullable = false)
    private UUID idUser;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public TaskModel() {
    }

    public TaskModel(UUID id, String description, String title, String priority, LocalDateTime startAt, LocalDateTime endAt, UUID idUser, LocalDateTime createdAt) {
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
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartAt() {
        return this.startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return this.endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public UUID getIdUser() {
        return this.idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TaskModel)) {
            return false;
        }
        TaskModel taskModel = (TaskModel) o;
        return Objects.equals(id, taskModel.id) && Objects.equals(description, taskModel.description) && Objects.equals(title, taskModel.title) && Objects.equals(priority, taskModel.priority) && Objects.equals(startAt, taskModel.startAt) && Objects.equals(endAt, taskModel.endAt) && Objects.equals(idUser, taskModel.idUser) && Objects.equals(createdAt, taskModel.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, title, priority, startAt, endAt, idUser, createdAt);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", description='" + getDescription() + "'" +
            ", title='" + getTitle() + "'" +
            ", priority='" + getPriority() + "'" +
            ", startAt='" + getStartAt() + "'" +
            ", endAt='" + getEndAt() + "'" +
            ", idUser='" + getIdUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }


}
