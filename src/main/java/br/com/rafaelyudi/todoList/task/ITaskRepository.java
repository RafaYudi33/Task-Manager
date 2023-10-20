package br.com.rafaelyudi.todoList.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    TaskModel findByTitle(String title); 
    List<TaskModel> findByIdUser(UUID idUser);
    void deleteById(UUID id);
    List<TaskModel> findByEndAtBetween(LocalDateTime currentDate, LocalDateTime plusOneDay); 

}
