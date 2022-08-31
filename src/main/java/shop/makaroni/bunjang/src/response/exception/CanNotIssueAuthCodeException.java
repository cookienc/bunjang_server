package shop.makaroni.bunjang.src.response.exception;

public class CanNotIssueAuthCodeException extends RuntimeException {
	public CanNotIssueAuthCodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
