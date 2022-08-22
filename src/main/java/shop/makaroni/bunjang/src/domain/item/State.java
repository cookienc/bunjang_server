package shop.makaroni.bunjang.src.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
	DELETE("D"), REGISTERED("R"), SELLING("Y"), SOLD("S");

	private String state;

	public static boolean valid(String condition) {
		return !(SOLD.getState().equals(condition) &&
				SELLING.getState().equals(condition) &&
				REGISTERED.getState().equals(condition));
	}
}
