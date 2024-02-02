package br.com.rafaelyudi.todoList.Task;


import java.util.List;
import java.util.UUID;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/tasks/v1")
@Tag(name = "Task" , description = "Endpoints to managing tasks")
public class TaskController {
    

    @Autowired
    private TaskService taskService; 

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDto, HttpServletRequest request){
       var taskCreated = this.taskService.createTask(taskDto, request);
       return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);     
    }
    
    @GetMapping(value = "{id}", produces =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TaskDTO> findTaskById(@PathVariable UUID id, HttpServletRequest request){
        var task = this.taskService.findTaskById(id, request); 
        return ResponseEntity.status(HttpStatus.OK).body(task); 
    }

    @GetMapping(value = "/", produces =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TaskDTO> getTaskEspecificUser(HttpServletRequest request){
        return taskService.getTaskEspecificUser(request);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO dataTask, HttpServletRequest request, @PathVariable UUID id){   
        var taskUpdated = this.taskService.updateTask(dataTask, request, id);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);  
    }
    
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> delete(HttpServletRequest request, @PathVariable UUID id){
        this.taskService.deleteTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada"); 
    }

}
