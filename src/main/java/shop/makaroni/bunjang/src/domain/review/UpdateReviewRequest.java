package shop.makaroni.bunjang.src.domain.review;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateReviewRequest {
	private Long itemIdx;
	private String post;
	private Double rating;
	private List<String> images;
}
