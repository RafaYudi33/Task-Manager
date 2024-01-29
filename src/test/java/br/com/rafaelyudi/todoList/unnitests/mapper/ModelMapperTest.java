package br.com.rafaelyudi.todoList.unnitests.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.rafaelyudi.todoList.Mapper.ModelMapperConverter;
import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockTask;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;

public class ModelMapperTest {
   
    MockUser inputUserObject; 
    MockTask inputTaskObject;

    @BeforeEach
    public void setUp(){
        inputUserObject = new MockUser();
        inputTaskObject = new MockTask();
    }
    
    @Test
    public void  testParseUserModelToUserDto(){
        UserModel user = inputUserObject.mockUserModel(1);
        UserDTO output = ModelMapperConverter.parseObject(user, UserDTO.class); 

        assertNotNull(output);
        assertEquals(user.getId(), output.getKey());
        assertEquals(user.getName(), output.getName());
        assertEquals(user.getUsername(), output.getUsername());
        assertEquals(user.getCreatedAt(), output.getCreatedAt());
        assertEquals(user.getEmail(), user.getEmail());
        assertEquals(user.getPassword(), output.getPassword()); 

        System.out.println(output.getKey().toString());
    }


    @Test
    public void  testParseUserDtoToUserModel(){
        UserDTO user = inputUserObject.mockUserDto(1);
        UserModel output = ModelMapperConverter.parseObject(user, UserModel.class); 

        assertNotNull(output);
        assertEquals(user.getKey(), output.getId());
        assertEquals(user.getName(), output.getName());
        assertEquals(user.getUsername(), output.getUsername());
        assertEquals(user.getCreatedAt(), output.getCreatedAt());
        assertEquals(user.getEmail(), user.getEmail());
        assertEquals(user.getPassword(), output.getPassword()); 
    }   

    @Test
    public void testParseTaskModelListToTaskDtoList(){
        List<TaskModel> tasks = inputTaskObject.mockListTaskModel();
        List<TaskDTO> outputList = ModelMapperConverter.parseListObject(tasks, TaskDTO.class); 

        TaskDTO outputZero = outputList.get(0); 
        assertEquals(tasks.get(0).getId(), outputZero.getKey());
        assertEquals(tasks.get(0).getTitle(), outputZero.getTitle());
        assertEquals(tasks.get(0).getDescription(), outputZero.getDescription());
        assertEquals(tasks.get(0).getPriority(), outputZero.getPriority());
        assertEquals(tasks.get(0).getStartAt(), outputZero.getStartAt());
        assertEquals(tasks.get(0).getEndAt(), outputZero.getEndAt());
        assertEquals(tasks.get(0).getCreatedAt(), outputZero.getCreatedAt());
        assertEquals(tasks.get(0).getIdUser(), outputZero.getIdUser());
        System.out.println(tasks.get(0).getId().toString());

        TaskDTO outputOne = outputList.get(1); 
        assertEquals(tasks.get(1).getId(), outputOne.getKey());
        assertEquals(tasks.get(1).getTitle(), outputOne.getTitle());
        assertEquals(tasks.get(1).getDescription(), outputOne.getDescription());
        assertEquals(tasks.get(1).getPriority(), outputOne.getPriority());
        assertEquals(tasks.get(1).getStartAt(), outputOne.getStartAt());
        assertEquals(tasks.get(1).getEndAt(), outputOne.getEndAt());
        assertEquals(tasks.get(1).getCreatedAt(), outputOne.getCreatedAt());
        assertEquals(tasks.get(1).getIdUser(), outputOne.getIdUser());
        System.out.println(tasks.get(1).getId().toString());
    }

    @Test
    public void testParseTaskDtoListToTaskModelList(){
        List<TaskDTO> tasks = inputTaskObject.mockListTaskDto();
        List<TaskModel> outputList = ModelMapperConverter.parseListObject(tasks, TaskModel.class); 

        TaskModel outputZero = outputList.get(0); 
        assertEquals(tasks.get(0).getKey(), outputZero.getId());
        assertEquals(tasks.get(0).getTitle(), outputZero.getTitle());
        assertEquals(tasks.get(0).getDescription(), outputZero.getDescription());
        assertEquals(tasks.get(0).getPriority(), outputZero.getPriority());
        assertEquals(tasks.get(0).getStartAt(), outputZero.getStartAt());
        assertEquals(tasks.get(0).getEndAt(), outputZero.getEndAt());
        assertEquals(tasks.get(0).getCreatedAt(), outputZero.getCreatedAt());
        assertEquals(tasks.get(0).getIdUser(), outputZero.getIdUser());
        

        TaskModel outputOne = outputList.get(1); 
        assertEquals(tasks.get(1).getKey(), outputOne.getId());
        assertEquals(tasks.get(1).getTitle(), outputOne.getTitle());
        assertEquals(tasks.get(1).getDescription(), outputOne.getDescription());
        assertEquals(tasks.get(1).getPriority(), outputOne.getPriority());
        assertEquals(tasks.get(1).getStartAt(), outputOne.getStartAt());
        assertEquals(tasks.get(1).getEndAt(), outputOne.getEndAt());
        assertEquals(tasks.get(1).getCreatedAt(), outputOne.getCreatedAt());
        assertEquals(tasks.get(1).getIdUser(), outputOne.getIdUser());
        
    }

}
