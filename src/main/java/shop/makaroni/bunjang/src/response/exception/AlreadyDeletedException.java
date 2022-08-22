package shop.makaroni.bunjang.src.response.exception;

public class AlreadyDeletedException extends RuntimeException {
	public AlreadyDeletedException(String message) {
		super(message);
	}
}
