package fun.oop.framework.exception;

public class ApiResponseException extends ApiException {
	private static final long serialVersionUID = 1L;
	public ApiResponseException(String message) {
		super(message);
	}

	public ApiResponseException(String message, Throwable cause) {
		super(message, cause);
	}
}
