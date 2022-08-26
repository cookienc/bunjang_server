package shop.makaroni.bunjang.src.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	DONT_PURCHASE_ITEM_EXCEPTION(HttpStatus.UNAUTHORIZED, Messages.DONT_PURCHASE_ITEM),
	CANNOT_FIND_PURCHASED_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.CANNOT_FIND_PURCHASED_ITEM),
	EMPTY_PARAM_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.EMPTY_PARAM),
	NOT_MATCH_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.NOT_MATCH_PASSWORD),
	NOT_RIGHT_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.NOT_RIGHT_PASSWORD),
	DUPLICATE_LOGIN_ID_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.DUPLICATE_LOGIN_ID),
	CANNOT_ENCODE_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, Messages.CANNOT_ENCODE_PASSWORD),
	CANNOT_DECODE_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, Messages.CANNOT_DECODE_PASSWORD),
	NO_SUCH_ELEMENT_EXCEPTION(HttpStatus.NOT_FOUND, Messages.NO_ELEMENT),
	ALREADY_DELETED_MEMBER_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_DELETED_MEMBER),
	ALREADY_DELETED_REVIEW_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_DELETED_REVIEW),
	MISSING_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_REQUIRED_PARAMETER),
	INVALID_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_RIGHT_PARAMETER);

	private static class Messages {
		public static final String DONT_PURCHASE_ITEM = "구매한 상품이 없습니다.";
		public static final String CANNOT_FIND_PURCHASED_ITEM = "구매한 상품을 찾을 수 없습니다.";
		public static final String EMPTY_PARAM = "필수 파라미터를 입력해주세요.";
		public static final String NOT_MATCH_PASSWORD = "아이디와 비밀번호가 일치하지 않습니다.";
		public static final String NOT_RIGHT_PASSWORD = "올바르지 않은 비밀번호 입니다.";
		public static final String DUPLICATE_LOGIN_ID = "중복된 아이디로 가입할 수 없습니다.";
		public static final String CANNOT_ENCODE_PASSWORD = "비밀번호를 암호화 할 수 없습니다.";
		public static final String CANNOT_DECODE_PASSWORD = "비밀번호를 복호화 할 수 없습니다.";
		public static final String NO_ELEMENT = "결과 값을 찾을 수 없습니다.";
		public static final String ALREADY_DELETED_MEMBER = "이미 탈퇴된 회원 입니다.";
		public static final String ALREADY_DELETED_REVIEW = "이미 리뷰 입니다.";
		public static final String PLEASE_ENTER_REQUIRED_PARAMETER = "필수 파라미터를 넣어주세요.";
		public static final String PLEASE_ENTER_RIGHT_PARAMETER = "올바른 파라미터를 넣어주세요.";
	}

	private HttpStatus status;
	private String messages;
}
