package br.com.rafaelyudi.todoList.Task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_tasks" )
@NoArgsConstructor
public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id; 
    private String description; 

    private String title; 
    private String priority; 
    private LocalDateTime startAt;
    private LocalDateTime endAt;   
    private UUID idUser; 
    @CreationTimestamp
    private LocalDateTime createdAt; 




}
