package br.com.rafaelyudi.todoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.rafaelyudi.todoList.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRepository; 


    @Autowired
    private TaskService taskService; 

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskDTO taskDto, HttpServletRequest request){
       var taskCreated = this.taskService.createTask(taskDto, request);
       return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);     
    }
    
    @GetMapping("")
    public List<TaskModel> lisAllTasks(){
        var tasks = this.taskRepository.findAll(); 
        return tasks; 
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser"); 
        var tasks = this.taskRepository.findByIdUser((UUID) idUser); 
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskDTO dataTask, HttpServletRequest request, @PathVariable UUID id){   
        var taskUpdated = this.taskService.updateTask(dataTask, request, id);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);  
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(HttpServletRequest request, @PathVariable UUID id){
        var task = this.taskRepository.findById(id).orElse(null); 
        var idUser = request.getAttribute("idUser"); 


        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada"); 
        }

        if(idUser == "Unauthorized" ||(!idUser.equals(task.getIdUser()))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não tem permissão para excluir a tarefa"); 
        }

        this.taskRepository.deleteById(id); 
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada"); 
    }

}
