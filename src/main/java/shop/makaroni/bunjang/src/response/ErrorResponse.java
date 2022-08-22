package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
	Integer code;
	String message;
	String path;

	public ErrorResponse(Integer code, String message, String path) {
		this.code = code;
		this.message = message;
		this.path = path;
	}

	public static ErrorResponse of(ErrorCode error, String requestUri) {
		return new ErrorResponse(error.getStatus().value(), error.getMessages(), requestUri);
	}
}
