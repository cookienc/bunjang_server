package shop.makaroni.bunjang.src.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.makaroni.bunjang.src.response.exception.InvalidInputEx;

import static shop.makaroni.bunjang.src.response.ErrorCode.INVALID_INPUT_EXCEPTION;

@Getter
@AllArgsConstructor
public enum State {
	DELETE("D"), REGISTERED("R"), SELLING("Y"), SOLD("S");

	private String state;

	public static void valid(String condition) {
		if (isNotRightCondition(condition)) {
			throw new InvalidInputEx(INVALID_INPUT_EXCEPTION.getMessages());
		}
	}

	private static boolean isNotRightCondition(String condition) {
		return !(SOLD.getState().equals(condition) &&
				SELLING.getState().equals(condition) &&
				REGISTERED.getState().equals(condition));
	}


}
