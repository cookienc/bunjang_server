package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessStatus {
	DELETE_REVIEW_SUCCESS(HttpStatus.NO_CONTENT, Messages.DELETE_REVIEW_SUCCESS),
	LOGIN_SUCCESS(HttpStatus.OK, Messages.LOGIN_SUCCESS),
	CHECK_LOGIN_ID_SUCCESS(HttpStatus.OK, Messages.CHECK_LOGIN_ID_SUCCESS),
	SAVE_SUCCESS(HttpStatus.CREATED, Messages.SAVE_SUCCESS),
	MEMBER_SAVE_SUCCESS(HttpStatus.CREATED, Messages.SAVE_SUCCESS),
	PATCH_SUCCESS(HttpStatus.OK, Messages.PATCH_SUCCESS),
	WITHDRAWAL_SUCCESS(HttpStatus.NO_CONTENT, Messages.WITHDRAWAL_SUCCESS);

	private static class Messages {
		public static String DELETE_REVIEW_SUCCESS= "리뷰를 삭제 하였습니다.";
		public static String LOGIN_SUCCESS= "로그인에 성공했습니다.";
		public static String CHECK_LOGIN_ID_SUCCESS = "중복된 아이디가 없습니다.";
		public static String SAVE_SUCCESS = "저장되었습니다.";
		public static String MEMBER_SAVE_SUCCESS = "회원가입이 성공했습니다.";
		public static String PATCH_SUCCESS = "변경되었습니다.";
		public static String WITHDRAWAL_SUCCESS = "회원 탈퇴 되었습니다.";
	}

	private HttpStatus status;
	private String message;

	SuccessStatus(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
