package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseInfo {
	private Integer code;
	private String message;

	public ResponseInfo(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ResponseInfo of(SuccessStatus status) {
		return new ResponseInfo(status.getStatus().value(), status.getMessage());
	}
}
