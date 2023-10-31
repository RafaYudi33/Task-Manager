package br.com.rafaelyudi.todoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

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

    public boolean verifyAuthorization(String idUser){
        
        if(idUser.equals("Unauthorized")){
            return false; 
        }

        return true;
    }

    public TaskModel createTask(TaskDTO data, HttpServletRequest request){
        TaskModel task = new TaskModel(data);
        var idUser = request.getAttribute("idUser"); 
        task.setIdUser((UUID)idUser); 
        saveTask(task);
        return task; 
    }

    public void saveTask(TaskModel task){
        this.taskRepository.save(task); 
    }
    
}
