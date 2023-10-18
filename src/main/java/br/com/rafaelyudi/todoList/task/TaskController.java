package br.com.rafaelyudi.todoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelyudi.todoList.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRepository; 

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
       
        if(idUser.equals("Unauthorized")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário e/ou senha incorretos"); 
        }

        taskModel.setIdUser((UUID)idUser);


        var currentDate = LocalDateTime.now();

        // se a data atual for depois da data de inicio da tarefa
        // significa que a tarefa foi iniciada antes de ser cadastrada
        if(taskModel.getStartAt().isBefore(currentDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de inicio da tarefa deve ser posterior à data atual");
        }

        // se a data de termino for antes que a data de inicio, algo está errado
        if(taskModel.getEndAt().isBefore(taskModel.getStartAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de término deve ser posterior a data de inicio");
        }

        var taskCreated = this.taskRepository.save(taskModel); 
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);     
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser"); 
        var tasks = this.taskRepository.findByIdUser((UUID) idUser); 
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){   
        var task = this.taskRepository.findById(id).orElse(null); 
        var idUser = request.getAttribute("idUser"); 

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }

        if(idUser == "Unauthorized"||(!task.getIdUser().equals(idUser))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não tem permissão para alterar esta tarefa"); 
        }

        Utils.copyPartialProp(taskModel, task);
        var taskUpdated = this.taskRepository.save(task); 
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);  
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable UUID id){
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
