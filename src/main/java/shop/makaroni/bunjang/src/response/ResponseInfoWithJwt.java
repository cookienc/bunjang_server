package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseInfoWithJwt {
	private String code;
	private String message;
	private String jwt;

	public ResponseInfoWithJwt(String code, String message, String jwt) {
		this.code = code;
		this.message = message;
		this.jwt = jwt;
	}

	public static ResponseInfoWithJwt of(SuccessStatus status, String jwt) {
		return new ResponseInfoWithJwt(String.valueOf(status.getStatus().value()), status.getMessage(), jwt);
	}
}
