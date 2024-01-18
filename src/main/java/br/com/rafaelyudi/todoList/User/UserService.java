package br.com.rafaelyudi.todoList.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService {
    
    @Autowired
    private IUserRepository userRepository; 

    @Autowired
    private ModelMapper modelMapper; 


    public String passCript(UserDTO data){
        //var user = this.userRepository.findByUsername(username);
        var passwordCript = BCrypt.withDefaults().hashToString(12, data.getPassword().toCharArray()); 
        return passwordCript; 
    }

   public UserDTO userCreate (UserDTO data){
        
        var passCript = passCript(data);
        var userModel = modelMapper.map(data, UserModel.class); 
        userModel.setPassword(passCript);
        userSave(userModel);
        
        var userDto = modelMapper.map(userModel, UserDTO.class); 
        return userDto; 
   }


   public void userSave(@NonNull UserModel user){
        this.userRepository.save(user); 
   }

}
