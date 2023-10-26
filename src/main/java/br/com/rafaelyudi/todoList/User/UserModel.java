package br.com.rafaelyudi.todoList.User;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//Disponibiliza os Getter e Setter 
@Data 
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
    private LocalDateTime createdAt; 

    UserModel(UserDTO data){
        this.username = data.username(); 
        this.name = data.name(); 
        this.password = data.password(); 
        this.email = data.email(); 
    }

}
