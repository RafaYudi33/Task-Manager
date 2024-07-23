package br.com.rafaelyudi.todoList.User;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoginResponseDTO(String token, LocalDateTime expirationTime) {

}
