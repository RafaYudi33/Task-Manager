package br.com.rafaelyudi.todoList.unnitests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rafaelyudi.todoList.Task.ITaskRepository;
import br.com.rafaelyudi.todoList.Task.TaskService;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockTask;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    MockTask inputObject; 

    @BeforeEach
    public void setUp(){
        inputObject = new MockTask(); 
        MockitoAnnotations.openMocks(this); 
    }

    @InjectMocks
    private TaskService service; 

    @Mock
    private ITaskRepository repository;



    @Test
    public void testCreateTask(){
        
    }

}
