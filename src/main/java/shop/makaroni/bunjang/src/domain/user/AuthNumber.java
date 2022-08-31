package shop.makaroni.bunjang.src.domain.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AuthNumber {

	@NotBlank(message = "인증번호를 입력해주세요.")
	private String authNumber;
}
