package br.com.rafaelyudi.todoList.Task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;



@Data
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
    
} 
    

