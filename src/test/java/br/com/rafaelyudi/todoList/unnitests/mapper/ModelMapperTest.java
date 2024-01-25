package br.com.rafaelyudi.todoList.unnitests.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.rafaelyudi.todoList.Mapper.ModelMapperConfig;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;

public class ModelMapperTest {
   
    MockUser inputObject; 

    @BeforeEach
    public void setUp(){
        inputObject = new MockUser();
    }
    
    @Test
    public void  parseUserModelToDto(){
        UserModel user = inputObject.mockUserModel(1);
        UserDTO output = ModelMapperConfig.parseObject(user, UserDTO.class); 

        assertNotNull(output);
        assertEquals(user.getId(), output.getKey());
        assertEquals(user.getName(), output.getName());
        assertEquals(user.getUsername(), output.getUsername());
        assertEquals(user.getCreatedAt(), output.getCreatedAt());
        assertEquals(user.getEmail(), user.getEmail());
        assertEquals(user.getPassword(), output.getPassword()); 
    }


    @Test
    public void  parseDtoToUserModel(){
        UserDTO user = inputObject.mockUserDto(1);
        UserModel output = ModelMapperConfig.parseObject(user, UserModel.class); 

        assertNotNull(output);
        assertEquals(user.getKey(), output.getId());
        assertEquals(user.getName(), output.getName());
        assertEquals(user.getUsername(), output.getUsername());
        assertEquals(user.getCreatedAt(), output.getCreatedAt());
        assertEquals(user.getEmail(), user.getEmail());
        assertEquals(user.getPassword(), output.getPassword()); 
    }

}
