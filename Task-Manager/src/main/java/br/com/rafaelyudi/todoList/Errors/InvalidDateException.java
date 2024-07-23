package br.com.rafaelyudi.todoList.Errors;

import java.io.Serializable;

public class InvalidDateException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public InvalidDateException(String message){
        super(message);
    }

}
 