package shop.makaroni.bunjang.src.domain.review;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UpdateReviewRequest {

	@NotBlank(message = "리뷰 내용을 입력해 주세요.")
	private String post;

	@NotNull(message = "평가를 입력해 주세요.")
	@Range(min = 0, max = 5, message = "평가를 0 ~ 5 사이로 입력해 주세요.")
	private Double rating;
	private List<String> images;
}
