package shop.makaroni.bunjang.src.domain.login;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LoginRequest {

	@NotNull(message = "아이디를 입력해주세요.")
	@NotBlank(message = "아이디를 입력해주세요.")
	String loginId;

	@NotNull(message = "비밀번호를 입력해주세요.")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password;
}
