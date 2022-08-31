package shop.makaroni.bunjang.src.response.exception;

public class AuthCodeNotMatchEx extends RuntimeException {
	public AuthCodeNotMatchEx(String message) {
		super(message);
	}
}
