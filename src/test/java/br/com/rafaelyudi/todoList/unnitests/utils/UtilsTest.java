package br.com.rafaelyudi.todoList.unnitests.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.rafaelyudi.todoList.User.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.Utils.Utils;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockTask;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UtilsTest {
    MockUser inputUser; 
    MockTask inputTask;
    Utils utils;

    @BeforeEach
    public void setUp(){
        inputUser = new MockUser(); 
        inputTask = new MockTask();
        utils = new Utils(); 
    }
    
    
    @Test
    @DisplayName("Should cript password when everything is ok")
    public void passCriptTest(){
         UserDTO user = inputUser.mockUserDto(1);

        var result = utils.passCript(user.getPassword());
        assertTrue(BCrypt.verifyer().verify(user.getPassword().toCharArray(), result).verified);
    }

    @Test 
    @DisplayName("Should copy null properties when everything is ok")
    public void testGetNullPropertyName(){
        TaskDTO task = inputTask.mockTaskDto(1); 
        task.setDescription(null);
        task.setPriority(null);
 
        String[] result = utils.getNullPropertyName(task);
         
        assertArrayEquals(new String[]{"description", "priority"}, result);

    }

    @Test
    @DisplayName("Should copy partial properties when everything is ok")
    public void testCopyPartialProperties(){
        TaskDTO taskWithPropToUpdated = new TaskDTO();
        TaskModel taskWillBeUpdated = inputTask.mockTaskModel(1); 
        taskWithPropToUpdated.setDescription("updated");

        utils.copyPartialProp(taskWithPropToUpdated, taskWillBeUpdated);
        assertEquals("updated", taskWillBeUpdated.getDescription());

        assertEquals("TitleTest1", taskWillBeUpdated.getTitle());
        assertEquals("PriorityTest1", taskWillBeUpdated.getPriority());
        assertEquals(LocalDateTime.of(2090, 1, 1, 1, 0, 0), taskWillBeUpdated.getStartAt());
        assertEquals(LocalDateTime.of(2090, 2, 2, 2, 0, 0), taskWillBeUpdated.getEndAt());
        assertEquals(LocalDateTime.of(2080, 1, 1, 1, 0, 0), taskWillBeUpdated.getCreatedAt());
        assertEquals(UUID.fromString("d8321483-b592-49ac-ba3b-46f32bea96ea"), taskWillBeUpdated.getId());
        assertEquals(UUID.fromString("e11cdccd-2087-469a-8521-34d6e67576c7"), taskWillBeUpdated.getIdUser());
        
    }

    @Test
    @DisplayName("Should only check the authorization when everything is ok")
    public void verifyAuthorizationCase1(){
        UUID mockIdUser = UUID.randomUUID();

        boolean result = utils.verifyAuthorization(mockIdUser);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should check the authorization and also check if the idUser is the same as the idUser of the task")
    public void verifyAuthorizationCase2(){

        TaskModel entity = inputTask.mockTaskModel(1);
        UUID mockIdUser = entity.getIdUser();


        boolean result = utils.verifyAuthorization(mockIdUser,  entity.getIdUser());
        assertTrue(result);
    }
}
