package shop.makaroni.bunjang.src.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhoneNumber {
	private String phoneNumber;

	public PhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
