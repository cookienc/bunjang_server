package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseInfoWithCheck {
	private String code;
	private String message;
	private boolean isCheck;

	public ResponseInfoWithCheck(String code, String message, boolean isCheck) {
		this.code = code;
		this.message = message;
		this.isCheck = isCheck;
	}

	public static ResponseInfoWithCheck of(SuccessStatus status, boolean isCheck) {
		return new ResponseInfoWithCheck(String.valueOf(status.getStatus().value()), status.getMessage(), isCheck);
	}
}
