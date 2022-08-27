package shop.makaroni.bunjang.src.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleReviewDto {
	private String reviewIdx;
	private String post;
	private String rating;
	private boolean hasComment;
}
