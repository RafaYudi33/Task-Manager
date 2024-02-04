package br.com.rafaelyudi.todoList.Task;


import br.com.rafaelyudi.todoList.Errors.CustomResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tasks/v1")
@Tag(name = "Task" , description = "Endpoints to managing tasks")
public class TaskController {
    

    @Autowired
    private TaskService taskService;



    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Create a task",
            description = "Create a task by passing in JSON or XML",
            tags = "Task",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = TaskDTO.class))
                            }
                    ),
                    @ApiResponse(description = "Invalid date", responseCode = "400", content = @Content(schema = @Schema(implementation = CustomResponseError.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(implementation = CustomResponseError.class))),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content(schema = @Schema(implementation = CustomResponseError.class)))
            }
    )

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
