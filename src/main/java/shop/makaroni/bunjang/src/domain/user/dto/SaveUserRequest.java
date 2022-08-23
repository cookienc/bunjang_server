package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SaveUserRequest {

	@NotNull(message = "아이디를 입력해주세요")
	@NotBlank(message = "아이디를 입력해주세요")
	private String loginId;

	@NotNull(message = "비밀번호를 입력해주세요")
	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password;

	private String storeName;

	@Builder
	public SaveUserRequest(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}
}
