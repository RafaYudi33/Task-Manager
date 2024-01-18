package br.com.rafaelyudi.todoList.User;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {

    private UUID id;
    private String username;
    private String name;
    private String password;
    private String email;
}
