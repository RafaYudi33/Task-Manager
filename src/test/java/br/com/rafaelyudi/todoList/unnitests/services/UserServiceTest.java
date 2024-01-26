package br.com.rafaelyudi.todoList.unnitests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

    // @Test
    // public void testPassCript() {
    //     UserDTO user = inputObject.mockUserDto(1);

    //     var result = service.passCript(user);
    //     assertTrue(BCrypt.verifyer().verify(user.getPassword().toCharArray(), result).verified);
    // }
        
    @Test
    public void testCreateUser(){
        UserDTO user = inputObject.mockUserDto(1); 
        UserModel entity = inputObject.mockUserModel(1); 
        UserModel userPersisted = entity;         

        when(repository.findByUsername(user.getUsername())).thenReturn(null);
        when(utils.passCript(user)).thenReturn("passwordTest1");
        when(repository.save(entity)).thenReturn(userPersisted); 
        

        var result = service.userCreate(user);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());
        assertEquals(user.getKey(), result.getKey());
        assertEquals("passwordTest1", result.getPassword());
        assertEquals(user.getName(), result.getName());
        
        assertTrue(result.getLinks().toString().contains("</tasks/>;rel=\"Criar uma tarefa\";type=\"POST\""));
    }



}
