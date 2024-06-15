package br.com.rafaelyudi.todoList.Errors;

public class TokenGenerationException extends RuntimeException{

    public TokenGenerationException(){
        super("Erro ao gerar o token!");
    }

}
