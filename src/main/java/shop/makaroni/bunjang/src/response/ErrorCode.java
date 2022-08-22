package shop.makaroni.bunjang.src.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	MISSING_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_REQUIRED_PARAMETER),
	INVALID_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, Messages.PLEASE_ENTER_RIGHT_PARAMETER);

	private static class Messages {
		public static final String PLEASE_ENTER_REQUIRED_PARAMETER = "필수 파라미터를 넣어주세요.";
		public static final String PLEASE_ENTER_RIGHT_PARAMETER = "올바른 파라미터를 넣어주세요.";
	}

	private HttpStatus status;
	private String messages;
}
