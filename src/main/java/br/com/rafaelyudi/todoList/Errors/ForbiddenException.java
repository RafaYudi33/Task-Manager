package br.com.rafaelyudi.todoList.Errors;

public class ForbiddenException extends RuntimeException{
    
    public ForbiddenException(String message){
        super(message); 
    }

    public ForbiddenException(){
        super("Usuário e/ou senha incorretos");
    }
}
