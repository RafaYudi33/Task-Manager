package br.com.rafaelyudi.todoList.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")   
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserDTO userData){ //objeto que será passado de parametro vem do body da requisição
        
        //validar se o usuário ja existe 
        var user = this.userRepository.findByUsername(userData.getUsername());
        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já Existe");
        }else{
            // se nao existe, realizar a criação
            var userCreated = this.userService.userCreate(userData);  
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated); 
        }
    }

}
