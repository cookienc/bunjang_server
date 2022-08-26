package shop.makaroni.bunjang.src.response.exception;

public class CannotFindPurchasedItem extends RuntimeException {
	public CannotFindPurchasedItem(String message) {
		super(message);
	}
}
