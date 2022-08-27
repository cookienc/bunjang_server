package shop.makaroni.bunjang.src.domain.review.view;

import lombok.Builder;
import lombok.Getter;
import shop.makaroni.bunjang.src.domain.review.dto.SingleReviewDto;

import java.util.List;

@Getter
public class SingleReviewResponse {
	private String reviewIdx;
	private String post;
	private String rating;
	private List<String> images;

	@Builder
	public SingleReviewResponse(String reviewIdx, String post, String rating, List<String> images) {
		this.reviewIdx = reviewIdx;
		this.post = post;
		this.rating = rating;
		this.images = images;
	}

	public static SingleReviewResponse of(SingleReviewDto review, List<String> images) {
		return SingleReviewResponse.builder()
				.reviewIdx(review.getReviewIdx())
				.post(review.getPost())
				.rating(review.getRating())
				.images(images)
				.build();
	}
}
