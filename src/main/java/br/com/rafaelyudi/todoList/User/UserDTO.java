package br.com.rafaelyudi.todoList.User;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;


@JsonPropertyOrder({"id", "username", "name", "password", "email", "createdAt"})
public class UserDTO extends RepresentationModel<UserDTO>{

    @JsonProperty("id")
    private UUID key;
    private String username;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createdAt;


    public UserDTO() {
    }

    public UserDTO(UUID key, String username, String name, String password, String email, LocalDateTime createdAt) {
        this.key = key;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getKey() {
        return this.key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

  

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserDTO)) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(key, userDTO.key) && Objects.equals(username, userDTO.username) && Objects.equals(name, userDTO.name) && Objects.equals(password, userDTO.password) && Objects.equals(email, userDTO.email) && Objects.equals(createdAt, userDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, username, name, password, email, createdAt);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", username='" + getUsername() + "'" +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }

    
}
