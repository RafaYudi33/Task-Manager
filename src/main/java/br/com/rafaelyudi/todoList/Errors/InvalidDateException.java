package br.com.rafaelyudi.todoList.Errors;

public class InvalidDateException extends RuntimeException{
    
    public InvalidDateException(String message){
        super(message);
    }

}
 