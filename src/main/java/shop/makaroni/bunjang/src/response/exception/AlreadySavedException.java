package shop.makaroni.bunjang.src.response.exception;

public class AlreadySavedException extends RuntimeException {
	public AlreadySavedException(String message) {
		super(message);
	}
}
