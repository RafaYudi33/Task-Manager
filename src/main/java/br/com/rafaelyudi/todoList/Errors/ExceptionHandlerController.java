package br.com.rafaelyudi.todoList.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestControllerAdvice

public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final CustomResponseError handleGenericException(Exception e ,WebRequest request){
        return new CustomResponseError(e.getMessage(),LocalDateTime.now(), request.getDescription(false));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public final CustomResponseError handleNotFoundException(NotFoundException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDateException.class)
    public final CustomResponseError handleInvalidDateException(InvalidDateException e, WebRequest request) {
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public final CustomResponseError handleUnauthorizedException(UnauthorizedException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public final CustomResponseError handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest request){
        return new CustomResponseError(e.getMessage(), LocalDateTime.now(), request.getDescription(false));
    }


    
}
