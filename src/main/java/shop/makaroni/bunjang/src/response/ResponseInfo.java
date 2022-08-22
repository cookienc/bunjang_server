package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseInfo {
	private String code;
	private String message;

	public ResponseInfo(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ResponseInfo of(SuccessStatus status) {
		return new ResponseInfo(String.valueOf(status.getStatus().value()), status.getMessage());
	}
}
