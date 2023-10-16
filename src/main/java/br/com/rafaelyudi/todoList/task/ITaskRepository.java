package br.com.rafaelyudi.todoList.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;




public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    TaskModel findByTitle(String title); 
    List<TaskModel> findByIdUser(UUID idUser);
}
