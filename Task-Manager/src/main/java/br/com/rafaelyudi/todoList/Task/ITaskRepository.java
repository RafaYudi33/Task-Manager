package br.com.rafaelyudi.todoList.Task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;



import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    void deleteById(@NonNull UUID id);
    List<TaskModel> findByEndAtBetween(LocalDateTime currentDate, LocalDateTime plusOneDay);

    List<TaskModel> findByStartAtBetween(LocalDateTime currentDate, LocalDateTime oneHourAtFuture);
    @NonNull
    Optional<TaskModel> findById(@NonNull UUID id);

    List<TaskModel> findByIdUser(UUID idUSer);
}
