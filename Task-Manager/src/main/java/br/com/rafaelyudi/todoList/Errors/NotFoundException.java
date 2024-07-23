package br.com.rafaelyudi.todoList.Errors;

public class NotFoundException extends RuntimeException{
        public NotFoundException(String message){
            super(message); 
        } 
}
