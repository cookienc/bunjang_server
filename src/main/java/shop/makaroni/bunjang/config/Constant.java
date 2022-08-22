package shop.makaroni.bunjang.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constant {
	CANNOT_FIND_LOCATION("지역정보를 찾을 수 없음");

	private String messages;
}

