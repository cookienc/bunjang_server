package shop.makaroni.bunjang.src.domain.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostReviewRequest {
	private Long itemIdx;
	private String post;
	private Double rating;
	private List<String> images;
}
