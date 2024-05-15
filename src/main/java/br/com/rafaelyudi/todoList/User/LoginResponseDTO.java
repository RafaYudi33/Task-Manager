package br.com.rafaelyudi.todoList.User;

import java.util.UUID;

public record LoginResponseDTO(String token, UUID idUser) {

}
