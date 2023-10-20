package br.com.rafaelyudi.todoList.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    
    @Autowired 
    private ITaskRepository taskRepository;

    public List<TaskModel> findTasksCloseEnd(){
        LocalDateTime currentDate = LocalDateTime.now(); 
        LocalDateTime oneDayForEnd = currentDate.plusDays(1);

        var tasks = this.taskRepository.findByEndAtBetween(currentDate, oneDayForEnd); 

        return tasks; 
    }
    
}
