package br.com.rafaelyudi.todoList.unnitests.services;


import br.com.rafaelyudi.todoList.Errors.InvalidDateException;
import br.com.rafaelyudi.todoList.Errors.NotFoundException;
import br.com.rafaelyudi.todoList.Task.ITaskRepository;
import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;
import br.com.rafaelyudi.todoList.Task.TaskService;
import br.com.rafaelyudi.todoList.Utils.Utils;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockTask;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    MockTask inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockTask();
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private TaskService service;

    @Mock
    private ITaskRepository repository;

    @Mock
    private Utils utils;

    @Mock
    HttpServletRequest request;

    @Test
    @DisplayName("Should create task when everything is ok")
    public void testCreateTaskCase1() {
        TaskDTO taskDTO = inputObject.mockTaskDto(1);
        TaskModel entity = inputObject.mockTaskModel(1);

        when(request.getAttribute("idUser")).thenReturn(entity.getIdUser());
        TaskDTO result = service.createTask(taskDTO, request);

        verify(request, times(1)).getAttribute("idUser");
        verify(repository, times(1)).save(entity);

        assertNotNull(result);
        assertEquals(result.getKey(), taskDTO.getKey());
        assertEquals(result.getCreatedAt(), taskDTO.getCreatedAt());
        assertEquals(result.getDescription(), taskDTO.getDescription());
        assertEquals(result.getEndAt(), taskDTO.getEndAt());
        assertEquals(result.getPriority(), taskDTO.getPriority());
        assertEquals(result.getTitle(), taskDTO.getTitle());
        assertEquals(result.getStartAt(), taskDTO.getStartAt());
        assertTrue(result.getLinks().toString()
                .contains("</tasks/v1/d8321483-b592-49ac-ba3b-46f32bea96ea>;rel=\"self\";type=\"GET\""));
    }

    @Test
    @DisplayName("Should throw an InvalidDateException when the start date is before or equal to the current date")
    public void testCreateTaskCase2() {
        TaskDTO task = inputObject.mockTaskDto(1);
        task.setStartAt(LocalDateTime.now().minusDays(1));
        task.setCreatedAt(LocalDateTime.now());

        Exception e = assertThrows(InvalidDateException.class, () -> service.createTask(task, request));

        String expectedMessage = "A data de início deve ser posterior a data atual";
        String actualMessage = e.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should throw an InvalidDateException when the end date is before or equal to the start date")
    public void testCreateTaskCase3() {
        TaskDTO task = inputObject.mockTaskDto(1);
        task.setEndAt(LocalDateTime.now());
        task.setStartAt(LocalDateTime.now().plusDays(1));

        Exception e = assertThrows(InvalidDateException.class, ()-> service.createTask(task, request));

        String expectedMessage = "A data de fim deve ser posterior a data de início";
        String actualMessage = e.getMessage();


        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @DisplayName("Should update task when everything is ok")
    public void testUpdateTaskCase1(){
        TaskDTO dataWithPropToUpdate = new TaskDTO();
        dataWithPropToUpdate.setDescription("updated");

        TaskModel entity = inputObject.mockTaskModel(1);

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(request.getAttribute("idUser")).thenReturn(entity.getIdUser());

        entity.setDescription("updated");
        when(utils.copyPartialProp(dataWithPropToUpdate, entity)).thenReturn(entity);

        TaskDTO result = service.updateTask(dataWithPropToUpdate, entity.getId(), request);

        verify(repository,times(1)).save(entity);
        verify(repository, times(1)).findById(entity.getId());
        verify(utils, times(1)).copyPartialProp(dataWithPropToUpdate, entity);
        verify(request, times(1)).getAttribute("idUser");

        assertNotNull(result);
        assertEquals(result.getDescription(), entity.getDescription());
        assertEquals(result.getKey(), entity.getId());
        assertEquals(result.getCreatedAt(), entity.getCreatedAt());
        assertEquals(result.getEndAt(), entity.getEndAt());
        assertEquals(result.getPriority(), entity.getPriority());
        assertEquals(result.getTitle(), entity.getTitle());
        assertEquals(result.getStartAt(), entity.getStartAt());
        assertEquals(result.getIdUser(), entity.getIdUser());
        assertTrue(result.getLinks().toString().contains("</tasks/v1/d8321483-b592-49ac-ba3b-46f32bea96ea>;rel=\"self\";type=\"GET\""));

    }

    @Test
    @DisplayName("Should throw NotFoundException when task not found")
    public void testUpdateCase2(){
       UUID mockId = UUID.randomUUID();


        when(repository.findById(mockId)).thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, ()-> service.updateTask(any(), mockId,request));

        String expectedMessage = "Tarefa não encontrada!";
        String actualMessage = e.getMessage();

        verify(repository,times(1)).findById(mockId);
        assertEquals(expectedMessage, actualMessage);

    }


    @Test
    @DisplayName("Should throw an InvalidDateException when the start date is before or equal to the current date")
    public void testUpdateTaskCase4() {
        TaskModel entity = inputObject.mockTaskModel(1);
        TaskDTO dataWithPropToUpdate = new TaskDTO();
        dataWithPropToUpdate.setStartAt(LocalDateTime.now());

        entity.setStartAt(dataWithPropToUpdate.getStartAt());

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(request.getAttribute("idUser")).thenReturn(entity.getIdUser());
        when(utils.copyPartialProp(dataWithPropToUpdate, entity)).thenReturn(entity);


        Exception e = assertThrows(InvalidDateException.class, () -> service.updateTask(dataWithPropToUpdate, entity.getId(),request));

        String expectedMessage = "A data de início deve ser posterior a data atual";
        String actualMessage = e.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(repository, times(1)).findById(entity.getId());
        verify(request,times(1)).getAttribute("idUser");
        verify(utils, times(1)).copyPartialProp(dataWithPropToUpdate,entity);
    }

    @Test
    @DisplayName("Should throw an InvalidDateException when the end date is before or equal to the start date")
    public void testUpdateTaskCase5() {
        TaskModel entity = inputObject.mockTaskModel(1);
        TaskDTO dataWithPropToUpdate = new TaskDTO();
        dataWithPropToUpdate.setEndAt(LocalDateTime.now());

        entity.setEndAt(dataWithPropToUpdate.getEndAt());


        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(request.getAttribute("idUser")).thenReturn(entity.getIdUser());
        when(utils.copyPartialProp(dataWithPropToUpdate, entity)).thenReturn(entity);

        Exception e = assertThrows(InvalidDateException.class, () -> service.updateTask(dataWithPropToUpdate, entity.getId(), request));

        String expectedMessage = "A data de fim deve ser posterior a data de início";
        String actualMessage = e.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(repository, times(1)).findById(entity.getId());
        verify(request,times(1)).getAttribute("idUser");
        verify(utils, times(1)).copyPartialProp(dataWithPropToUpdate,entity);
    }

    @Test
    @DisplayName("Should delete task when everything is ok")
    public void testDeleteTaskCase1(){

        TaskModel entity = inputObject.mockTaskModel(1);
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        service.deleteTask(entity.getId());
        verify(repository, times(1)).findById(entity.getId());
        verify(repository, times(1)).delete(entity);

    }

    @Test
    @DisplayName("Should throw NotFoundException when task not found")
    public void testDeleteTaskCase2(){
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, ()-> service.deleteTask(any()));

        String expectedMessage = "Tarefa não encontrada!";
        String actualMessage = e.getMessage();

        verify(repository, times(1)).findById(any());
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @DisplayName("Should find tasks to especific user when everything is ok")
    public void testGetTaskESpecificUserCase1(){
        UUID mockIdUser = UUID.randomUUID();
        List<TaskModel> listTaskModel = inputObject.mockListTaskModel();


        when(request.getAttribute("idUser")).thenReturn(mockIdUser);
        when(repository.findByIdUser(mockIdUser)).thenReturn(listTaskModel);

        var result = service.getTaskSpecificUser(request);
        int counter = 0;
        for (TaskDTO r : result) {
            assertEquals(r.getKey(), listTaskModel.get(counter).getId());
            assertEquals(r.getIdUser(), listTaskModel.get(counter).getIdUser());
            assertEquals(r.getCreatedAt(), listTaskModel.get(counter).getCreatedAt());
            assertEquals(r.getDescription(), listTaskModel.get(counter).getDescription());
            assertEquals(r.getEndAt(), listTaskModel.get(counter).getEndAt());
            assertEquals(r.getPriority(), listTaskModel.get(counter).getPriority());
            assertEquals(r.getStartAt(), listTaskModel.get(counter).getStartAt());
            assertEquals(r.getTitle(), listTaskModel.get(counter).getTitle());
            assertTrue(r.getLinks().toString().contains("</tasks/v1/"+ r.getKey() +">;rel=\"self\""));
            counter++;
        }

        verify(request,times(1)).getAttribute("idUser");
        verify(repository,times(1)).findByIdUser(mockIdUser);
    }


    @Test
    @DisplayName("Should find task by id when everything is ok")
    public void testFindTaskByIdCase1() {
        TaskModel entity = inputObject.mockTaskModel(1);

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        var result = service.findTaskById(entity.getId());



        assertNotNull(result);
        assertEquals(result.getTitle(), entity.getTitle());
        assertEquals(result.getDescription(), entity.getDescription());
        assertEquals(result.getCreatedAt(), entity.getCreatedAt());
        assertEquals(result.getStartAt(), entity.getStartAt());
        assertEquals(result.getIdUser(), entity.getIdUser());
        assertEquals(result.getEndAt(), entity.getEndAt());
        assertEquals(result.getKey(), entity.getId());
        assertEquals(result.getPriority(), entity.getPriority());
        assertTrue(result.getLinks().toString().contains(
                "</tasks/v1/userTasks>;rel=\"Listar todas as tarefas do mesmo usuário\";type=\"GET\",</tasks/v1/>;rel=\"Criar outra tarefa\";type=\"POST\",</tasks/v1/d8321483-b592-49ac-ba3b-46f32bea96ea>;rel=\"Deletar esta tarefa\";type=\"DELETE\",</tasks/v1/d8321483-b592-49ac-ba3b-46f32bea96ea>;rel=\"Modificar esta tarefa\";type=\"PUT\""));

        verify(repository,times(1)).findById(entity.getId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when task is not found")
    public void testFindTaskByIdCase2() {
        UUID mockID = UUID.randomUUID();
        when(repository.findById(mockID)).thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> service.findTaskById(mockID));

        String expectedMessage = "Tarefa não encontrada!";
        String actualMessage = e.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
