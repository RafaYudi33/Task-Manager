package br.com.rafaelyudi.todoList.User;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.rafaelyudi.todoList.Security.Role;
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
    private Role role;
    public UserDTO() {
    }

    public UserDTO(UUID key, String username, String name, String password, String email, LocalDateTime createdAt) {
        this.key = key;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.role = Role.USER;

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

    public UserDTO key(UUID key) {
        setKey(key);
        return this;
    }

    public UserDTO username(String username) {
        setUsername(username);
        return this;
    }

    public UserDTO name(String name) {
        setName(name);
        return this;
    }

    public UserDTO password(String password) {
        setPassword(password);
        return this;
    }

    public UserDTO email(String email) {
        setEmail(email);
        return this;
    }

    public UserDTO createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        UserDTO userDTO = (UserDTO) object;
        return Objects.equals(getKey(), userDTO.getKey()) && Objects.equals(getUsername(), userDTO.getUsername()) && Objects.equals(getName(), userDTO.getName()) && Objects.equals(getPassword(), userDTO.getPassword()) && Objects.equals(getEmail(), userDTO.getEmail()) && Objects.equals(getCreatedAt(), userDTO.getCreatedAt()) && role == userDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getUsername(), getName(), getPassword(), getEmail(), getCreatedAt(), role);
    }
}
