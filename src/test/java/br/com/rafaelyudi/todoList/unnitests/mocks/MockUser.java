package br.com.rafaelyudi.todoList.unnitests.mocks;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;

public class MockUser {
    
    public UserModel mockUserModel(int number){
        UserModel user = new UserModel(); 
        user.setId(UUID.fromString("55392fd7-e418-4b88-b5e0-8924a53964f3"));   
        user.setName("UserTest" + number);
        user.setEmail("EmailTest" + number);
        user.setPassword("passwordTest" + number);
        user.setCreatedAt(LocalDateTime.of(2024, 01, 30, 10, 00, 00)); 
        return user;
    }
    
    public UserDTO mockUserDto(int number){
        UserDTO user = new UserDTO();
        user.setKey(UUID.fromString("55392fd7-e418-4b88-b5e0-8924a53964f3"));   
        user.setName("UserTest" + number);
        user.setEmail("EmailTest" + number);
        user.setPassword("passwordTest" + number);
        user.setCreatedAt(LocalDateTime.of(2024, 01, 30, 10, 00, 00)); 
        return user; 
    }

}
