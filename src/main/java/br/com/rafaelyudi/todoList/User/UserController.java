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
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userData) { 

        var userCreated = this.userService.userCreate(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}

