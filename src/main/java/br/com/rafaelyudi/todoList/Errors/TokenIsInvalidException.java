package br.com.rafaelyudi.todoList.Errors;

public class TokenIsInvalidException extends RuntimeException {

    public TokenIsInvalidException(){
        super("Token inválido!");
    }

}
