package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
public class BeanErrorResponse {
	private String code;
	private String filed;
	private String message;

	public BeanErrorResponse(String code, String filed, String message) {
		this.code = code;
		this.filed = filed;
		this.message = message;
	}

	public static BeanErrorResponse of(FieldError fieldError) {
		return new BeanErrorResponse(fieldError.getCode(), fieldError.getField(), fieldError.getDefaultMessage());
	}
}
