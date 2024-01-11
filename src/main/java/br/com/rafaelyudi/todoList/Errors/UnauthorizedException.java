package br.com.rafaelyudi.todoList.Errors;

public class UnauthorizedException extends RuntimeException{
    
    public UnauthorizedException(String message){
        super(message); 
    }
}
