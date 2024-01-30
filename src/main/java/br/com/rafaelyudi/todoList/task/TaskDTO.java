package br.com.rafaelyudi.todoList.Task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;




@JsonPropertyOrder({"id", "description", "title", "priority", "startAt", "endAt", "idUser", "createdAt"})
public class TaskDTO extends RepresentationModel<TaskDTO>{

    @JsonProperty("id")
    private UUID key; 
    private String description; 
    private String title; 
    private String priority; 
    private LocalDateTime startAt;
    private LocalDateTime endAt;   
    private UUID idUser; 
    private LocalDateTime createdAt; 


    public TaskDTO() {
    }

    public TaskDTO(UUID key, String description, String title, String priority, LocalDateTime startAt, LocalDateTime endAt, UUID idUser, LocalDateTime createdAt) {
        this.key = key;
        this.description = description;
        this.title = title;
        this.priority = priority;
        this.startAt = startAt;
        this.endAt = endAt;
        this.idUser = idUser;
        this.createdAt = createdAt;
    }

  
    public UUID getKey() {
        return this.key;
    }

    public void setKey(UUID key) {
        this.key = key;
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
        if (!(o instanceof TaskDTO)) {
            return false;
        }
        TaskDTO taskDTO = (TaskDTO) o;
        return Objects.equals(key, taskDTO.key) && Objects.equals(description, taskDTO.description) && Objects.equals(title, taskDTO.title) && Objects.equals(priority, taskDTO.priority) && Objects.equals(startAt, taskDTO.startAt) && Objects.equals(endAt, taskDTO.endAt) && Objects.equals(idUser, taskDTO.idUser) && Objects.equals(createdAt, taskDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, description, title, priority, startAt, endAt, idUser, createdAt);
    }
    
    
} 
    

