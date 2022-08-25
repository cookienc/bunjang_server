package shop.makaroni.bunjang.src.domain.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSpecificDto {
	private Long idx;
	private Long itemIdx;
	private String reviewerIdx;
	private String reviewerName;
	private String reviewerImage;
	private String purchasedProduct;
	private String rating;
	private String reviewPost;
	private String reviewDate;
}
