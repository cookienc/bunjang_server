package shop.makaroni.bunjang.src.response.exception;

public class DuplicateLoginIdEx extends RuntimeException {
	public DuplicateLoginIdEx(String message) {
		super(message);
	}
}
