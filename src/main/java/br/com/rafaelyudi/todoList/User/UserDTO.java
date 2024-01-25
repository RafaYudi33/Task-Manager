package br.com.rafaelyudi.todoList.User;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "username", "name", "password", "email", "createdAt"})
public class UserDTO extends RepresentationModel<UserDTO>{

    @JsonProperty("id")
    private UUID key;
    private String username;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createdAt;
}
