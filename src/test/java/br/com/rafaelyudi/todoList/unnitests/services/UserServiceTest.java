package br.com.rafaelyudi.todoList.unnitests.services;

import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.User.IUserRepository;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;
import br.com.rafaelyudi.todoList.User.UserService;
import br.com.rafaelyudi.todoList.Utils.Utils;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    MockUser inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockUser();
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private UserService service;

    @Mock
    private IUserRepository repository;

    @Mock
    private Utils utils; 


    @Test
    @DisplayName("Should create task when everything is ok.")
    public void testCreateUserCase1(){
        UserDTO user = inputObject.mockUserDto(1); 
        UserModel entity = inputObject.mockUserModel(1); 
              

        when(repository.findByUsername(user.getUsername())).thenReturn(null);
        when(utils.passCript(user.getPassword())).thenReturn("passwordTest1");
        when(repository.save(entity)).thenReturn(entity);  
        

        var result = service.userCreate(user);

        verify(repository, times(1)).findByUsername(user.getUsername());
        verify(utils,times(1)).passCript(user.getPassword());
        verify(repository,times(1)).save(entity);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());
        assertEquals(user.getKey(), result.getKey());
        assertEquals("passwordTest1", result.getPassword());
        assertEquals(user.getName(), result.getName());
        
        assertTrue(result.getLinks().toString().contains("</tasks/>;rel=\"Criar sua primeira tarefa\";type=\"POST\""));
    }

    @Test
    @DisplayName("Should throw Exception when user already exists")
    public void testCreateUserCase2(){
        UserDTO user = inputObject.mockUserDto(1); 
        UserModel entity = inputObject.mockUserModel(1);

        when(repository.findByUsername(user.getUsername())).thenReturn(entity);

        Exception e = assertThrows(UserAlreadyExistsException.class, () -> service.userCreate(user));
        
        String expectedMessage = "Esse nome de usuário ja existe!";
        String actualMessage = e.getMessage();

        verify(repository, times(1)).findByUsername(user.getUsername());
        assertEquals(expectedMessage, actualMessage);
    }

}
