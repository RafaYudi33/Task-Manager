package br.com.rafaelyudi.todoList.User;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Type;

import java.util.Objects;


@Entity(name = "tb_users")
public class UserModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username; 
    private String name; 
    private String password; 
    private String email;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; 


    public UserModel() {
    }

    public UserModel(UUID id, String username, String name, String password, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UserModel id(UUID id) {
        setId(id);
        return this;
    }

    public UserModel username(String username) {
        setUsername(username);
        return this;
    }

    public UserModel name(String name) {
        setName(name);
        return this;
    }

    public UserModel password(String password) {
        setPassword(password);
        return this;
    }

    public UserModel email(String email) {
        setEmail(email);
        return this;
    }

    public UserModel createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserModel)) {
            return false;
        }
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(username, userModel.username) && Objects.equals(name, userModel.name) && Objects.equals(password, userModel.password) && Objects.equals(email, userModel.email) && Objects.equals(createdAt, userModel.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, password, email, createdAt);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
    
}
