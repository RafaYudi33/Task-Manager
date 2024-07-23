package br.com.rafaelyudi.todoList.unnitests.services;

import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.Security.Role;
import br.com.rafaelyudi.todoList.User.IUserRepository;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;
import br.com.rafaelyudi.todoList.User.UserService;
import br.com.rafaelyudi.todoList.Utils.Utils;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;
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
import org.springframework.security.crypto.password.PasswordEncoder;

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
    PasswordEncoder passwordEncoder;

    @Mock
    HttpServletRequest request;


    @Test
    @DisplayName("Should create user when everything is ok.")
    public void testCreateUserCase1(){
        UserDTO user = inputObject.mockUserDto(1); 
        UserModel entity = inputObject.mockUserModel(1);


        when(repository.findByUsername(user.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(repository.save(entity)).thenReturn(entity);
        entity.setRole(Role.USER);
        

        var result = service.userCreate(user);

        verify(repository, times(1)).findByUsername(user.getUsername());
        verify(passwordEncoder,times(1)).encode(user.getPassword());
        verify(repository,times(1)).save(entity);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());
        assertEquals(user.getKey(), result.getKey());
        assertEquals("passwordTest1", result.getPassword());
        assertEquals(user.getName(), result.getName());
        
        assertTrue(result.getLinks().toString().contains("</users/v1/login>;rel=\"Fazer login\";type=\"POST\""));
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

    @Test
    @DisplayName("Should delete a user when everything is ok")
    public void testeDeleteUserCase1(){
        UserModel entity = inputObject.mockUserModel(1);
        when(request.getAttribute("idUser")).thenReturn(entity.getId());

        service.delete(request);

        verify(repository, times(1)).deleteById(entity.getId());
    }

}
