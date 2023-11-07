package br.com.rafaelyudi.todoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelyudi.todoList.Errors.InvalidDateException;
import br.com.rafaelyudi.todoList.Errors.NotFoundException;
import br.com.rafaelyudi.todoList.Utils.Utils;
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

    boolean dateValidation(TaskDTO data) throws InvalidDateException{

        LocalDateTime currentDate = LocalDateTime.now(); 
        if(data.startAt().isBefore(currentDate)){
            throw new InvalidDateException("A data de início deve ser posterior a data atual");  
        }

        if(data.endAt().isBefore(data.startAt())){
            throw new InvalidDateException("A data de fim deve ser posterior a data de início"); 
        }

        return true;
    }

    public boolean verifyAuthorization(Object idUser){
        
        if(idUser.equals("Unauthorized")){
            return false; 
        }
        return true;
    }

    public boolean verifyAuthorization(Object idUser, Object idUserFromRepository){
        
        if(!verifyAuthorization(idUser)||!idUser.equals(idUserFromRepository)){
            return false;
        }
        
        return true; 
    }

    public TaskModel createTask(TaskDTO data, HttpServletRequest request){
        
        try{
            dateValidation(data); 
            var idUser = request.getAttribute("idUser"); 
            
            if(!verifyAuthorization(idUser.toString())){
                throw new NotFoundException("Não é possivel criar esta tarefa, usuário e/ou senha incorretos"); 
            }
            
            TaskModel task = new TaskModel(data);
            task.setIdUser((UUID)idUser); 
            saveTask(task);
            return task; 
        }catch(InvalidDateException e){
            throw e; 
        }
        
    }

    public TaskModel updateTask(TaskDTO dataTask, HttpServletRequest request, UUID id){
        var task = this.taskRepository.findById(id); 
        var idUser = request.getAttribute("idUser"); 
        

        if(task.isPresent()){
            TaskModel taskUpdate = task.get(); 

            UUID idUserFromRepository = taskUpdate.getIdUser(); 

            if(!verifyAuthorization(idUser, idUserFromRepository)){
                throw new NotFoundException("Tarefa não pode ser alterada, usuário e/ou senha incorretos");
            }else{
                Utils.copyPartialProp(dataTask, taskUpdate);
                saveTask(taskUpdate);
                return taskUpdate; 
            }
        }
        throw new NotFoundException("Tarefa não encontrada"); 
    }

    public void saveTask(TaskModel task){
        this.taskRepository.save(task); 
    }
    
}
