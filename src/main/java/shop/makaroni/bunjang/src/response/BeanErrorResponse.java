package shop.makaroni.bunjang.src.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
public class BeanErrorResponse {
	private String code;
	private String field;
	private String message;

	public BeanErrorResponse(String code, String field, String message) {
		this.code = code;
		this.field = field;
		this.message = message;
	}

	public static BeanErrorResponse of(FieldError fieldError) {
		return new BeanErrorResponse(fieldError.getCode(), fieldError.getField(), fieldError.getDefaultMessage());
	}
}
