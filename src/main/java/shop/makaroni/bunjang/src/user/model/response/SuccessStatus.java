package shop.makaroni.bunjang.src.user.model.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessStatus {
	PATCH_SUCCESS(HttpStatus.OK, Messages.PATCH_SUCCESS),
	WITHDRAWAL_SUCCESS(HttpStatus.NO_CONTENT, Messages.WITHDRAWAL_SUCCESS);

	private static class Messages {
		public static String PATCH_SUCCESS = "변경되었습니다.";
		public static String WITHDRAWAL_SUCCESS = "회원 탈되 되었습니다.";
	}

	private HttpStatus status;
	private String message;

	SuccessStatus(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
