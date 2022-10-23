package one.digitalinnovation.gof.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException{

    public ClienteNaoEncontradoException() {
        super("Não foi encontrado nenhum cliente com o ID informado");
    }
}
