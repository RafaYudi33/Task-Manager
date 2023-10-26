package br.com.rafaelyudi.todoList.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {
    
    @Autowired
    private IUserRepository userRepository; 


    public void passCript(String username, String password){
        
        var user = this.userRepository.findByUsername(username);

        var passwordCript = BCrypt.withDefaults().hashToString(12, password.toCharArray()); 
        user.setPassword(passwordCript);
    }

   public UserModel userCreate (UserDTO data){
        var userCreated = new UserModel(data);
        userSave(userCreated);
        passCript(userCreated.getUsername(), userCreated.getPassword());
        return userCreated; 
   }

   public void userSave(UserModel user){
        this.userRepository.save(user); 
   }

}
