package shop.makaroni.bunjang.src.user.model.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessStatus {
	PATCH_SUCCESS(HttpStatus.OK, Messages.PATCH_SUCCESS);

	private static class Messages {
		public static String PATCH_SUCCESS = "수정에 성공했습니다.";
	}

	private HttpStatus status;
	private String message;

	SuccessStatus(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
