package br.com.rafaelyudi.todoList.Errors;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

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
