package br.com.rafaelyudi.todoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelyudi.todoList.Errors.NotFoundException;
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

    public boolean verifyExists(String idUser){
        
        if(idUser.equals("Unauthorized")){
            return false; 
        }

        return true;
    }

    public TaskModel createTask(TaskDTO data, HttpServletRequest request){
        
        var idUser = request.getAttribute("idUser"); 
        if(!verifyExists(idUser.toString())){
            throw new NotFoundException("Não é possivel criar esta tarefa, nome de usuário e/ou senha incorretos"); 
        }
        
        TaskModel task = new TaskModel(data);
        task.setIdUser((UUID)idUser); 
        saveTask(task);
        return task; 
    }

    public void saveTask(TaskModel task){
        this.taskRepository.save(task); 
    }
    
}
