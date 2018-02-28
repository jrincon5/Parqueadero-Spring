package co.com.ceiba.parqueadero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ParqueaderoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ParqueaderoException(String message) {
		super(message);
	}
}
