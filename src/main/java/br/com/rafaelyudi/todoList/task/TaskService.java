package br.com.rafaelyudi.todoList.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelyudi.todoList.Errors.InvalidDateException;
import br.com.rafaelyudi.todoList.Errors.NotFoundException;
import br.com.rafaelyudi.todoList.Errors.UnauthorizedException;
import br.com.rafaelyudi.todoList.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {
    
    @Autowired 
    private ITaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper; 

    public List<TaskModel> findTasksCloseEnd(){
        LocalDateTime currentDate = LocalDateTime.now(); 
        LocalDateTime oneDayForEnd = currentDate.plusDays(1);

        var tasks = this.taskRepository.findByEndAtBetween(currentDate, oneDayForEnd); 

        return tasks; 
    }

    boolean dateValidation(TaskDTO data) throws InvalidDateException{

        LocalDateTime currentDate = LocalDateTime.now(); 
        if(data.getStartAt().isBefore(currentDate)){
            throw new InvalidDateException("A data de início deve ser posterior a data atual");  
        }

        if(data.getEndAt().isBefore(data.getStartAt())){
            throw new InvalidDateException("A data de fim deve ser posterior a data de início"); 
        }

        return true;
    }

    public boolean verifyAuthorization(Object idUser){
        
        if(idUser.equals("Unauthorized")){
            throw new UnauthorizedException("Usuário e/ou senha incorretos"); 
        }
        return true;
    }

    public boolean verifyAuthorization(Object idUser, Object idUserFromRepository){
        
        if(!verifyAuthorization(idUser)||!idUser.equals(idUserFromRepository)){
            throw new UnauthorizedException("Usuário e/ou senha incorretos"); 
        }
        
        return true; 
    }


    public TaskDTO createTask(TaskDTO data, HttpServletRequest request){
        
            dateValidation(data); 
            var idUser = request.getAttribute("idUser"); 
            verifyAuthorization(idUser.toString());
            TaskModel task = modelMapper.map(data, TaskModel.class);
            task.setIdUser((UUID)idUser); 
            saveTask(task);
            return modelMapper.map(task, TaskDTO.class);  
       
    }



    public TaskDTO updateTask(TaskDTO dataTask, HttpServletRequest request, UUID id){
        var task = this.taskRepository.findById(id); 
        var idUser = request.getAttribute("idUser"); 
       

        if(task.isPresent()){
            TaskModel taskUpdate = task.get(); 
            verifyAuthorization(idUser, taskUpdate.getIdUser());
            Utils.copyPartialProp(dataTask, taskUpdate);
            saveTask(taskUpdate);
            var taskDTO = modelMapper.map(taskUpdate, TaskDTO.class); 
            return taskDTO; 
            
        }
        throw new NotFoundException("Tarefa não encontrada"); 
    }



    public void deleteTask(UUID id, HttpServletRequest request){
       var task = taskRepository.findById(id);
       var idUser = request.getAttribute("idUser"); 
       
       if(task.isPresent()){

            TaskModel taskModel = task.get();
            verifyAuthorization(idUser, taskModel.getIdUser()); 
            this.taskRepository.delete(taskModel);
        }
        throw new NotFoundException("Tarefa não encontrada"); 
    }


    public List<TaskDTO> getTaskEspecificUser(HttpServletRequest request){

        var idUser = request.getAttribute("idUser"); 
        verifyAuthorization(idUser);
        var tasks = taskRepository.findByIdUser((UUID) idUser);
        List<TaskDTO> tasksDTOs = new ArrayList<>(); 
        
        for (TaskModel taskModel : tasks) {
            tasksDTOs.add(modelMapper.map(taskModel, TaskDTO.class));  
        }
        
        return tasksDTOs;

    }

    public void saveTask(TaskModel task){

        
        this.taskRepository.save(task); 
    }
    
    
}
