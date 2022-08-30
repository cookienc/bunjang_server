package shop.makaroni.bunjang.src.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	DONT_PURCHASE_ITEM_EXCEPTION(HttpStatus.UNAUTHORIZED, Messages.DONT_PURCHASE_ITEM),
	UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, Messages.UNAUTHORIZED_ACCESS),
	DO_LOGIN_FIRST_EXCEPTION(HttpStatus.UNAUTHORIZED, Messages.DO_LOGIN_FIRST),

	NO_SUCH_ELEMENT_EXCEPTION(HttpStatus.NOT_FOUND, Messages.NO_ELEMENT),

	EMPTY_PARAM_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.EMPTY_PARAM),
	DUPLICATE_LOGIN_ID_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.DUPLICATE_LOGIN_ID),
	CANNOT_FIND_PURCHASED_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.CANNOT_FIND_PURCHASED_ITEM),
	MISSING_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_REQUIRED_PARAMETER),
	INVALID_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_RIGHT_PARAMETER),
	NOT_MATCH_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.NOT_MATCH_PASSWORD),
	NOT_RIGHT_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.NOT_RIGHT_PASSWORD),
	NOT_EXIST_FOLLOW_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.NOT_EXIST_FOLLOW),

	ALREADY_SAVED_REVIEW(HttpStatus.BAD_REQUEST, Messages.ALREADY_SAVED_REVIEW),
	ALREADY_SAVED_FOLLOW_REVIEW(HttpStatus.BAD_REQUEST, Messages.ALREADY_SAVED_FOLLOW),
	ALREADY_HAS_COMMENT_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_HAS_COMMENTED),
	ALREADY_NOTIFICATION_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_NOTIFICATION),
	ALREADY_DELETED_MEMBER_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_DELETED_MEMBER),
	ALREADY_DELETED_REVIEW_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_DELETED_REVIEW),
	ALREADY_DELETED_REVIEW_COMMENT_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_DELETED_REVIEW_COMMENT),

	CANNOT_ENCODE_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, Messages.CANNOT_ENCODE_PASSWORD),
	CANNOT_DECODE_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, Messages.CANNOT_DECODE_PASSWORD),
	CANNOT_PARSING_OBJECT(HttpStatus.INTERNAL_SERVER_ERROR, Messages.CANNOT_PARSING_OBJECT);

	private static class Messages {
		public static final String DONT_PURCHASE_ITEM = "구매한 상품이 없습니다.";
		public static final String UNAUTHORIZED_ACCESS = "잘못된 접근입니다.";
		public static final String DO_LOGIN_FIRST = "로그인을 먼저 해주세요.";

		public static final String NO_ELEMENT = "결과 값을 찾을 수 없습니다.";

		public static final String EMPTY_PARAM = "필수 파라미터를 입력해주세요.";
		public static final String DUPLICATE_LOGIN_ID = "중복된 아이디로 가입할 수 없습니다.";
		public static final String CANNOT_FIND_PURCHASED_ITEM = "구매한 상품을 찾을 수 없습니다.";
		public static final String PLEASE_ENTER_REQUIRED_PARAMETER = "필수 파라미터를 넣어주세요.";
		public static final String PLEASE_ENTER_RIGHT_PARAMETER = "올바른 파라미터를 넣어주세요.";
		public static final String NOT_MATCH_PASSWORD = "아이디와 비밀번호가 일치하지 않습니다.";
		public static final String NOT_RIGHT_PASSWORD = "올바르지 않은 비밀번호 입니다.";
		public static final String NOT_EXIST_FOLLOW = "팔로우를 먼저 해주세요.";

		public static final String ALREADY_SAVED_REVIEW= "이미 등록된 리뷰 입니다.";
		public static final String ALREADY_SAVED_FOLLOW= "이미 등록된 팔로우 입니다.";
		public static final String ALREADY_HAS_COMMENTED = "이미 답글이 있습니다.";
		public static final String ALREADY_NOTIFICATION = "이미 알림 설정이 되어 있습니다.";
		public static final String ALREADY_DELETED_MEMBER = "이미 탈퇴된 회원 입니다.";
		public static final String ALREADY_DELETED_REVIEW = "이미 삭제된 리뷰 입니다.";
		public static final String ALREADY_DELETED_REVIEW_COMMENT = "이미 삭제된 댓글 입니다.";

		public static final String CANNOT_ENCODE_PASSWORD = "비밀번호를 암호화 할 수 없습니다.";
		public static final String CANNOT_DECODE_PASSWORD = "비밀번호를 복호화 할 수 없습니다.";
		public static final String CANNOT_PARSING_OBJECT = "객체를 만들 수 없습니다.";
	}

	private HttpStatus status;
	private String messages;
}
