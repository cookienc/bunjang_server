package shop.makaroni.bunjang.src.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.makaroni.bunjang.src.response.ErrorCode;
import shop.makaroni.bunjang.src.response.exception.AlreadySavedException;
import shop.makaroni.bunjang.src.response.exception.InvalidInputEx;

import static shop.makaroni.bunjang.src.response.ErrorCode.INVALID_INPUT_EXCEPTION;

@Getter
@AllArgsConstructor
public enum State {
	DELETE("D"), REGISTERED("R"), SELLING("Y"), SOLD("S"),
	NORMAL("Y"), NOT_SAVED("NS");

	private String state;

	public static void valid(String condition) {
		if (isRightCondition(condition)) {
			return;
		}
		throw new InvalidInputEx(INVALID_INPUT_EXCEPTION.getMessages());
	}

	private static boolean isRightCondition(String condition) {
		return SELLING.getState().equals(condition) ||SOLD.getState().equals(condition) || REGISTERED.getState().equals(condition);
	}


	public static void isAlreadySaved(String state) {
		if (NORMAL.getState().equals(state)) {
			throw new AlreadySavedException(ErrorCode.ALREADY_SAVED_REVIEW.getMessages());
		}
	}

	public static boolean isAlreadyDeleted(String state) {
		return DELETE.getState().equals(state);
	}
}
