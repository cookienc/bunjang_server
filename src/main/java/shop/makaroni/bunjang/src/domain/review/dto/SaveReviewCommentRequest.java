package shop.makaroni.bunjang.src.domain.review.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SaveReviewCommentRequest {

	@NotBlank(message = "내용을 입력해 주세요.")
	private String post;
}
