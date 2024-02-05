package br.com.rafaelyudi.todoList.Task;


import br.com.rafaelyudi.todoList.Config.Swagger.TaskExampleRequestBody;
import br.com.rafaelyudi.todoList.Errors.CustomResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tasks/v1")
@Tag(name = "Task" , description = "Endpoints to managing tasks")
@SecurityScheme(name = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
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
                    @ApiResponse(description = "Created", responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = TaskDTO.class))
                    }
                    ),
                    @ApiResponse(description = "Invalid date", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    ),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    )
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = TaskExampleRequestBody.class))
    )
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDto, HttpServletRequest request) {

        var taskCreated = this.taskService.createTask(taskDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);

    }
    
    @GetMapping(value = "{id}", produces =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Find a task",
            description = "Find a task by your id",
            tags = "Task",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = TaskDTO.class)),
                            @Content(schema = @Schema(implementation = TaskDTO.class))
                    }
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    ),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    )
            }
    )
    public ResponseEntity<TaskDTO> findTaskById(
            @Parameter(description = "The id of the task to find") @PathVariable UUID id,
            HttpServletRequest request
    ) {
        var task = this.taskService.findTaskById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }


    @GetMapping(value = "/", produces =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Find tasks",
            description = "Finds all of a user's tasks",
            tags = "Task",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class))),
                            @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class)))
                    }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    })
            }
    )
    public List<TaskDTO> getTaskSpecificUser(HttpServletRequest request){
        return taskService.getTaskSpecificUser(request);
    }


    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

    @Operation(
            summary = "Update a task",
            description = "Update a task by passing changes in JSON or XML",
            tags = "Task",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = TaskDTO.class)),
                    }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Invalid date", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }
                    )


            }

    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TaskExampleRequestBody.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = TaskExampleRequestBody.class))

            }
    )
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO dataTask, HttpServletRequest request, @PathVariable UUID id){   
        var taskUpdated = this.taskService.updateTask(dataTask, request, id);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);  
    }
    
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Delete a task",
            tags = "Task",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204", content = @Content),

                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    })
            }
    )
    public ResponseEntity<String> delete(HttpServletRequest request, @PathVariable UUID id){
        this.taskService.deleteTask(id, request);
        return ResponseEntity.noContent().build();
    }

}
