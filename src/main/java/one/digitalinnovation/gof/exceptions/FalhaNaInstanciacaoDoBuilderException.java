package one.digitalinnovation.gof.exceptions;

public class FalhaNaInstanciacaoDoBuilderException extends RuntimeException {
    public FalhaNaInstanciacaoDoBuilderException(ReflectiveOperationException e, String classe) {
        super("Falha na instanciação do builder da classe " + classe);
    }
}
