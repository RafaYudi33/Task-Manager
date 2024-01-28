package br.com.rafaelyudi.todoList.unnitests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

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

import br.com.rafaelyudi.todoList.Errors.NotFoundException;
import br.com.rafaelyudi.todoList.Errors.UnauthorizedException;
import br.com.rafaelyudi.todoList.Task.ITaskRepository;
import br.com.rafaelyudi.todoList.Task.TaskModel;
import br.com.rafaelyudi.todoList.Task.TaskService;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockTask;
import jakarta.servlet.http.HttpServletRequest;

@TestInstance(Lifecycle.PER_CLASS)
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

    HttpServletRequest request = mock(HttpServletRequest.class);



    @Test
    @DisplayName("")
    public void testCreateUserCase1(){

    }


    @Test
    @DisplayName("Should find task by id when everything is ok")
    public void testFindTaskByIdCase1() {
        TaskModel entity = inputObject.mockTaskModel(1);
        

        when(request.getAttribute("idUser")).thenReturn(entity.getId());
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        var result = service.findTaskById(entity.getId(), request);
        assertNotNull(result);
        assertEquals(result.getTitle(), entity.getTitle());
        assertEquals(result.getDescription(), entity.getDescription());
        assertEquals(result.getCreatedAt(), entity.getCreatedAt());
        assertEquals(result.getStartAt(), entity.getStartAt());
        assertEquals(result.getIdUser(), entity.getIdUser());
        assertEquals(result.getEndAt(), entity.getEndAt());
        assertEquals(result.getKey(), entity.getId());
        assertEquals(result.getPriority(), entity.getPriority());
        System.out.println(result.getLinks().toString());
        assertTrue(result.getLinks().toString().contains(
                "</tasks/>;rel=\"Listar todas as tarefas do mesmo usuário\";type=\"GET\",</tasks/>;rel=\"Criar outra tarefa\";type=\"POST\",</tasks/d8321483-b592-49ac-ba3b-46f32bea96ea>;rel=\"Deletar esta tarefa\";type=\"DELETE\",</tasks/d8321483-b592-49ac-ba3b-46f32bea96ea>;rel=\"Modificar esta tarefa\";type=\"PUT\""));
    }

    @Test
    @DisplayName("Should throw NotFoundException when task is not found")
    public void testFindTaskByIdCase2(){
        UUID mockID = UUID.randomUUID();
        when(repository.findById(mockID)).thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () ->{
            service.findTaskById(mockID, request);
        });

        String expectedMessage = "Tarefa não encontrada!";   
        String actualMessage = e.getMessage();
        
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when task is not found")
    public void testFindTaskByIdCase3(){
        TaskModel entity = inputObject.mockTaskModel(1); 

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity)); 
        when(request.getAttribute("idUser")).thenReturn("Unauthorized"); 

        Exception e = assertThrows(UnauthorizedException.class, () -> {
            service.findTaskById(entity.getId(), request);
        });

        String expectedMessage = "Usuário e/ou senha incorretos"; 
        String actualMessage = e.getMessage(); 

        assertEquals(expectedMessage, actualMessage);

    }

}
