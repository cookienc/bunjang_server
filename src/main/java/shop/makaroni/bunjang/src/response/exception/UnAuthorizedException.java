package shop.makaroni.bunjang.src.response.exception;

public class UnAuthorizedException extends RuntimeException {
	public UnAuthorizedException(String message) {
		super(message);
	}
}
