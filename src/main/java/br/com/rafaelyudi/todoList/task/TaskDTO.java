package br.com.rafaelyudi.todoList.Task;

import java.time.LocalDateTime;
import java.util.UUID;


import lombok.Data;



@Data
public class TaskDTO {

    private UUID id; 
    private String description; 
    private String title; 
    private String priority; 
    private LocalDateTime startAt;
    private LocalDateTime endAt;   
    private UUID idUser; 
    private LocalDateTime createdAt; 
    
} 
    

