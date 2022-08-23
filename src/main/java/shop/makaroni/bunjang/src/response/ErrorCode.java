package shop.makaroni.bunjang.src.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	CANNOT_ENCODE_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, Messages.CANNOT_ENCODE_PASSWORD),
	NO_SUCH_ELEMENT_EXCEPTION(HttpStatus.NOT_FOUND, Messages.NO_ELEMENT),
	ALREADY_DELETED_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.ALREADY_DELETED),
	MISSING_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_REQUIRED_PARAMETER),
	INVALID_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_RIGHT_PARAMETER);

	private static class Messages {
		public static final String CANNOT_ENCODE_PASSWORD = "비밀번호를 암호화 할 수 없습니다..";
		public static final String NO_ELEMENT = "결과 값을 찾을 수 없습니다.";
		public static final String ALREADY_DELETED = "이미 탈퇴된 회원입니다.";
		public static final String PLEASE_ENTER_REQUIRED_PARAMETER = "필수 파라미터를 넣어주세요.";
		public static final String PLEASE_ENTER_RIGHT_PARAMETER = "올바른 파라미터를 넣어주세요.";
	}

	private HttpStatus status;
	private String messages;
}
