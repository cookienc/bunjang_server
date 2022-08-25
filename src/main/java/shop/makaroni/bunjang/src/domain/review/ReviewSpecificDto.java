package shop.makaroni.bunjang.src.domain.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSpecificDto {
	private Long idx;
	private String reviewerIdx;
	private String reviewerName;
	private String reviewerImage;
	private String purchasedProduct;
	private String rating;
	private String reviewPost;
	private String reviewDate;

	@Builder
	public ReviewSpecificDto(Long idx, String reviewerIdx, String reviewerName, String reviewerImage, String purchasedProduct, String rating, String reviewPost, String reviewDate) {
		this.idx = idx;
		this.reviewerIdx = reviewerIdx;
		this.reviewerName = reviewerName;
		this.reviewerImage = reviewerImage;
		this.purchasedProduct = purchasedProduct;
		this.rating = rating;
		this.reviewPost = reviewPost;
		this.reviewDate = reviewDate;
	}
}
