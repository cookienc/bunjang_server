package shop.makaroni.bunjang.src.domain.review;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewSpecificView {
	private String reviewerIdx;
	private String reviewerName;
	private String reviewerImage;
	private String purchasedProduct;
	private String rating;
	private String reviewPost;
	private String reviewDate;

	private ReviewCommentDto comments;

	@Builder
	public ReviewSpecificView(String reviewerIdx, String reviewerName, String reviewerImage, String purchasedProduct, String rating, String reviewPost, String reviewDate, ReviewCommentDto comments) {
		this.reviewerIdx = reviewerIdx;
		this.reviewerName = reviewerName;
		this.reviewerImage = reviewerImage;
		this.purchasedProduct = purchasedProduct;
		this.rating = rating;
		this.reviewPost = reviewPost;
		this.reviewDate = reviewDate;
		this.comments = comments;
	}

	public static ReviewSpecificView of(ReviewSpecificDto reviewDto, ReviewCommentDto commentDto) {
		return ReviewSpecificView.builder()
				.reviewerIdx(reviewDto.getReviewerIdx())
				.reviewerName(reviewDto.getReviewerName())
				.reviewerImage(reviewDto.getReviewerImage())
				.purchasedProduct(reviewDto.getPurchasedProduct())
				.rating(reviewDto.getRating())
				.reviewPost(reviewDto.getReviewPost())
				.reviewDate(reviewDto.getReviewDate())
				.comments(commentDto)
				.build();
	}
}
