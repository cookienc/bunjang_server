package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessStatus {
	FOLLOW_SUCCESS(HttpStatus.OK, Messages.FOLLOW_SUCCESS),
	LOGIN_SUCCESS(HttpStatus.OK, Messages.LOGIN_SUCCESS),
	PATCH_SUCCESS(HttpStatus.OK, Messages.PATCH_SUCCESS),
	UPDATE_REVIEW_SUCCESS(HttpStatus.OK, Messages.UPDATE_REVEIEW_SUCCESS),
	UPDATE_REVIEW_COMMENT_SUCCESS(HttpStatus.OK, Messages.UPDATE_REVEIEW_COMMENT_SUCCESS),
	CHECK_LOGIN_ID_SUCCESS(HttpStatus.OK, Messages.CHECK_LOGIN_ID_SUCCESS),

	SAVE_SUCCESS(HttpStatus.CREATED, Messages.SAVE_SUCCESS),
	SAVE_NOTIFICATION_SUCCESS(HttpStatus.CREATED, Messages.SAVE_NOTIFICATION_SUCCESS),
	MEMBER_SAVE_SUCCESS(HttpStatus.CREATED, Messages.MEMBER_SAVE_SUCCESS),
	SAVE_REVIEW_COMMENT_SUCCESS(HttpStatus.CREATED, Messages.SAVE_REVIEW_COMMENT_SUCCESS),

	DELETE_REVIEW_SUCCESS(HttpStatus.NO_CONTENT, Messages.DELETE_REVIEW_SUCCESS),
	DELETE_FOLLOW_SUCCESS(HttpStatus.NO_CONTENT, Messages.DELETE_FOLLOW_SUCCESS),
	DELETE_REVIEW_COMMENT_SUCCESS(HttpStatus.NO_CONTENT, Messages.DELETE_REVIEW_COMMENT_SUCCESS),
	WITHDRAWAL_SUCCESS(HttpStatus.NO_CONTENT, Messages.WITHDRAWAL_SUCCESS);

	private static class Messages {
		public static String FOLLOW_SUCCESS= "팔로우에 성공 했습니다";
		public static String LOGIN_SUCCESS= "로그인에 성공 했습니다.";
		public static String PATCH_SUCCESS = "변경되었습니다.";
		public static String UPDATE_REVEIEW_SUCCESS= "리뷰를 수정 했습니다.";
		public static String UPDATE_REVEIEW_COMMENT_SUCCESS= "댓글을 수정 했습니다.";
		public static String CHECK_LOGIN_ID_SUCCESS = "중복된 아이디가 없습니다.";

		public static String SAVE_SUCCESS = "저장 되었습니다.";
		public static String SAVE_NOTIFICATION_SUCCESS = "알람 설정 되었습니다.";
		public static String MEMBER_SAVE_SUCCESS = "회원가입이 성공 했습니다.";
		public static String SAVE_REVIEW_COMMENT_SUCCESS= "댓글을 저장 했습니다.";

		public static String DELETE_REVIEW_SUCCESS= "리뷰를 삭제 하였습니다.";
		public static String DELETE_FOLLOW_SUCCESS= "팔로우를 삭제 하였습니다.";
		public static String DELETE_REVIEW_COMMENT_SUCCESS= "댓글이 삭제 되었습니다.";
		public static String WITHDRAWAL_SUCCESS = "회원 탈퇴 되었습니다.";
	}

	private HttpStatus status;
	private String message;

	SuccessStatus(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}