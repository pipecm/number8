package cl.pipecm.forum.user.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final int code;
	
	public UserException(final int code, final String message) {
		super(message);
		this.code = code;
	}
	
	public UserException(final HttpStatus status) {
		this(status.value(), status.name());
	}
}
