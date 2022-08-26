package shop.makaroni.bunjang.src.response.exception;

public class DontPurchaseItemEx extends RuntimeException {
	public DontPurchaseItemEx(String message) {
		super(message);
	}
}
