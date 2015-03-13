package mx.com.villavicencio.commons.exception;

/**
 *
 * @author Gabriel J
 */

public class ApplicationException extends RuntimeException{
    
    public ApplicationException(Throwable cause){
        super(cause);
    }
    
    public ApplicationException(String message, Throwable cause){
        super(message,cause);
    }
    
    public ApplicationException(String message){
        super(message);
    }
}
