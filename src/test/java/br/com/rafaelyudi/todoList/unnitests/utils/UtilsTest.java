package br.com.rafaelyudi.todoList.unnitests.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.Utils.Utils;
import br.com.rafaelyudi.todoList.unnitests.mocks.MockUser;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UtilsTest {
    MockUser inputObject; 
    Utils utils;

    @BeforeEach
    public void setUp(){
        inputObject = new MockUser(); 
        utils = new Utils(); 
    }
    
    

    @Test
    public void passCriptTest(){
         UserDTO user = inputObject.mockUserDto(1);

        var result = utils.passCript(user);
        assertTrue(BCrypt.verifyer().verify(user.getPassword().toCharArray(), result).verified);
    }


}
