package br.com.rafaelyudi.todoList.User;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "username", "name", "password", "email"})
public class UserDTO {

    @JsonProperty("id")
    private UUID key;
    private String username;
    private String name;
    private String password;
    private String email;
}
