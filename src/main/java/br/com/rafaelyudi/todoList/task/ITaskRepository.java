package br.com.rafaelyudi.todoList.Task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;



import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    TaskModel findByTitle(String title); 
    List<TaskModel> findByIdUser(UUID idUser);
    
    
    void deleteById(@NonNull UUID id);
    List<TaskModel> findByEndAtBetween(LocalDateTime currentDate, LocalDateTime plusOneDay); 

    Optional<TaskModel> findById(@NonNull UUID id);
}
