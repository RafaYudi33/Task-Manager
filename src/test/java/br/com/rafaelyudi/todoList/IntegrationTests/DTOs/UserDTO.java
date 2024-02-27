package br.com.rafaelyudi.todoList.IntegrationTests.DTOs;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createdAt;

    public UserDTO(UUID id, String username, String name, String password, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDto = (UserDTO) o;
        return Objects.equals(getId(), userDto.getId()) && Objects.equals(getUsername(), userDto.getUsername()) && Objects.equals(getName(), userDto.getName()) && Objects.equals(getPassword(), userDto.getPassword()) && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getCreatedAt(), userDto.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getName(), getPassword(), getEmail(), getCreatedAt());
    }
}
