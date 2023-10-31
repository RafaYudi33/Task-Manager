package br.com.rafaelyudi.todoList.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {
    
    @Autowired
    private IUserRepository userRepository; 


    public String passCript(UserDTO data){
        //var user = this.userRepository.findByUsername(username);
        var passwordCript = BCrypt.withDefaults().hashToString(12, data.password().toCharArray()); 
        return passwordCript; 
    }

   public UserModel userCreate (UserDTO data){
        var userCreated = new UserModel(data);
        var passCript = passCript(data);
        userCreated.setPassword(passCript);
        userSave(userCreated);
        return userCreated; 
   }

   public void userSave(UserModel user){
        this.userRepository.save(user); 
   }

}
