package br.com.rafaelyudi.todoList.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import br.com.rafaelyudi.todoList.Security.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;


@Entity(name = "tb_users")
public class UserModel implements UserDetails {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username; 
    private String name; 
    private String password; 
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;


    
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         if(this.role == Role.ADMIN) return List.of(
                 new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")
         );
         else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserModel userModel = (UserModel) object;
        return Objects.equals(getId(), userModel.getId()) && Objects.equals(getUsername(), userModel.getUsername()) && Objects.equals(getName(), userModel.getName()) && Objects.equals(getPassword(), userModel.getPassword()) && Objects.equals(getEmail(), userModel.getEmail()) && getRole() == userModel.getRole() && Objects.equals(getCreatedAt(), userModel.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getName(), getPassword(), getEmail(), getRole(), getCreatedAt());
    }
}
