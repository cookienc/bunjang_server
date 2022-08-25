package shop.makaroni.bunjang.src.domain.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewCommentDto {
	public static final String NO_CONTENT = "댓글이 없습니다.";
	private String sellerName;
	private String sellerPost;
	private String sellerDate;

	@Builder
	public ReviewCommentDto(String sellerName, String sellerPost, String sellerDate) {
		this.sellerName = sellerName;
		this.sellerPost = sellerPost;
		this.sellerDate = sellerDate;
	}

	public static ReviewCommentDto mock() {
		return ReviewCommentDto.builder()
				.sellerName(NO_CONTENT)
				.sellerPost(NO_CONTENT)
				.sellerDate(NO_CONTENT)
				.build();
	}
}
