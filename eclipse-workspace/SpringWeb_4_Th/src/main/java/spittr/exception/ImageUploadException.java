package spittr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE , reason="Upload file is failed") //Map exception to HTTP Status
public class ImageUploadException extends RuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8850355312211959529L;
	
	
	public ImageUploadException(String message) {
		super(message);
	}

}
