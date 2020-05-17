package cl.pipecm.forum.user.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import cl.pipecm.forum.user.common.ServiceResponse;

@ControllerAdvice
@SuppressWarnings("rawtypes")
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserExceptionHandler.class);
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ServiceResponse> handleUserException(UserException exception) {
		LOGGER.debug(exception.getMessage(), exception);
		ServiceResponse response = ServiceResponse.builder()
										.code(exception.getCode())
										.message(exception.getMessage())
										.timestamp(now())
										.build();
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ServiceResponse> handleOtherExceptions(Exception exception) {
		LOGGER.debug(exception.getMessage(), exception);
		ServiceResponse response = ServiceResponse.builder()
										.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
										.message(HttpStatus.INTERNAL_SERVER_ERROR.name())
										.timestamp(now())
										.build();
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
	
	private Date now() {
		return new Date();
	}
}
