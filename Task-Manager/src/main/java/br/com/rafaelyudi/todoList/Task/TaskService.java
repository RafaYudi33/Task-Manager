package br.com.rafaelyudi.todoList.Task;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

import br.com.rafaelyudi.todoList.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import br.com.rafaelyudi.todoList.Errors.InvalidDateException;
import br.com.rafaelyudi.todoList.Errors.NotFoundException;
import br.com.rafaelyudi.todoList.Mapper.ModelMapperConverter;
import br.com.rafaelyudi.todoList.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private Utils utils;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public List<TaskModel> findTasksCloseStart() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime oneHourForStart = currentDate.plusHours(1);

        return this.taskRepository.findByStartAtBetween(currentDate, oneHourForStart);
    }


    void dateValidation(TaskDTO data) throws InvalidDateException {

        LocalDateTime currentDate = LocalDateTime.now();
        if (data.getStartAt().isBefore(currentDate)) {
            throw new InvalidDateException("A data de início deve ser posterior a data atual");
        }

        if (data.getEndAt().isBefore(data.getStartAt())) {
            throw new InvalidDateException("A data de fim deve ser posterior a data de início");
        }

    }

    public TaskDTO createTask(TaskDTO data, HttpServletRequest request) {
        logger.info("Register a task!");
        dateValidation(data);
        TaskModel task = ModelMapperConverter.parseObject(data, TaskModel.class);

        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID)idUser);
        saveTask(task);

        var taskDto = ModelMapperConverter.parseObject(task, TaskDTO.class);
        taskDto.add(linkTo(methodOn(TaskController.class).findTaskById(taskDto.getKey())).withSelfRel()
                .withType("GET"));

        return taskDto;
    }

    public TaskDTO updateTask(TaskDTO dataTask, UUID id, HttpServletRequest request) {
        logger.info("Update a task!");

        var task = this.taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));

        var taskUpdated = utils.copyPartialProp(dataTask, task);
        var idUser = request.getAttribute("idUser");
        taskUpdated.setIdUser((UUID)idUser);

        var taskDTO = ModelMapperConverter.parseObject(taskUpdated, TaskDTO.class);
        dateValidation(taskDTO);
        saveTask(task);

        taskDTO.add(linkTo(methodOn(TaskController.class).findTaskById(id)).withSelfRel().withType("GET"));


        return taskDTO;

    }

    public TaskDTO findTaskById(UUID id) {
        logger.info("Find a task by your ID");
        TaskModel taskModel = this.taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Tarefa não encontrada!"));

        TaskDTO taskDto = ModelMapperConverter.parseObject(taskModel, TaskDTO.class);

        /* HATEOAS */
        taskDto.add(linkTo(methodOn(TaskController.class).getTaskSpecificUser( null))
                .withRel("Listar todas as tarefas do mesmo usuário").withType("GET"));
        taskDto.add(linkTo(methodOn(TaskController.class).create(taskDto, null)).withRel("Criar outra tarefa")
                .withType("POST"));
        taskDto.add(linkTo(methodOn(TaskController.class).delete(id)).withRel("Deletar esta tarefa")
                .withType("DELETE"));
        taskDto.add(linkTo(methodOn(TaskController.class).update(taskDto, id, null)).withRel("Modificar esta tarefa")
                .withType("PUT"));

        return taskDto;
    }

    public void deleteTask(@NonNull UUID id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));
        this.taskRepository.delete(task);
    }

    public List<TaskDTO> getTaskSpecificUser(HttpServletRequest request) {
        logger.info("Get a task from specific user!");
        var idUser = request.getAttribute("idUser");
        var tasks = taskRepository.findByIdUser((UUID)idUser);
        var tasksDTO = ModelMapperConverter.parseListObject(tasks, TaskDTO.class);

        for (TaskDTO t : tasksDTO) {
            try {
                t.add(linkTo(methodOn(TaskController.class).findTaskById(t.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao adicionar link!");
            }
        }
        return tasksDTO;
    }

    public void saveTask(TaskModel task) {
        this.taskRepository.save(task);
    }

}
