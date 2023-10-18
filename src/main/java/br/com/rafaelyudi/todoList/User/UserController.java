package br.com.rafaelyudi.todoList.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")   
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){ //objeto que será passado de parametro vem do body da requisição
        
        //validar se o usuário ja existe 
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já Existe");
        }

        var passwordCript = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()); 
        userModel.setPassword(passwordCript);
        
        // se nao existe, realizar a criação
        var userCreated = this.userRepository.save(userModel); 
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated); 
    }

}
