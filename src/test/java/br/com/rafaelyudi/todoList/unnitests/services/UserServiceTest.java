package br.com.rafaelyudi.todoList.unnitests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.User.IUserRepository;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;
import br.com.rafaelyudi.todoList.User.UserService;
import br.com.rafaelyudi.todoList.Utils.Utils;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;

@TestInstance(Lifecycle.PER_CLASS)
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
        when(utils.passCript(user)).thenReturn("passwordTest1");
         
        

        var result = service.userCreate(user);
        verify(repository, times(1)).save(entity);
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

        Exception e = assertThrows(UserAlreadyExistsException.class, () ->{
            service.userCreate(user);
        }); 
        
        String expectedMessage = "Esse nome de usuário ja existe!";
        String actualMessage = e.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
