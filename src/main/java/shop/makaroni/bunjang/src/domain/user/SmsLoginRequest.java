package shop.makaroni.bunjang.src.domain.user;

import lombok.Getter;

@Getter
public class SmsLoginRequest {
	private String name;
	private String birthNumber;
	private String phoneNumber;
	private Boolean isChecked;
}
