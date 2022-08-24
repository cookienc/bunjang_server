package shop.makaroni.bunjang.src.response.exception;

public class DoesNotMatchPasswordEx extends RuntimeException {
	public DoesNotMatchPasswordEx(String message) {
		super(message);
	}
}
