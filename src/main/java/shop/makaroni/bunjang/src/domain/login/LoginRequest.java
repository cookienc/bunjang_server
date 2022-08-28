package shop.makaroni.bunjang.src.domain.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotNull(message = "아이디를 입력해주세요.")
	@NotBlank(message = "아이디를 입력해주세요.")
	String loginId;

	@NotNull(message = "비밀번호를 입력해주세요.")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password;

	public static LoginRequest of(Long id, String password) {
		return new LoginRequest(String.valueOf(id), password);
	}

	public static LoginRequest of(String id, String password) {
		return new LoginRequest(id, password);
	}
}
