package shop.makaroni.bunjang.src.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewAllResponse {
	private String reviewerName;
	private String reviewerImage;
	private String post;
	private String rating;
	private String date;

	@Builder
	public ReviewAllResponse(String reviewerName, String reviewerImage, String post, String rating, String date) {
		this.reviewerName = reviewerName;
		this.reviewerImage = reviewerImage;
		this.post = post;
		this.rating = rating;
		this.date = date;
	}
}
