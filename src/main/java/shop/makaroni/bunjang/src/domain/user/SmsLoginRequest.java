package shop.makaroni.bunjang.src.domain.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SmsLoginRequest {
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;

	@NotBlank(message = "주민등록번호를 입력해주세요.")
	private String birthNumber;

	@NotBlank(message = "전화번호를 입력해주세요.")
	private String phoneNumber;

	@NotNull(message = "인증 유무를 입력해주세요.")
	private Boolean isChecked;
}
