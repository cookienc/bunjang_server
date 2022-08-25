package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.review.ReviewSpecificView;
import shop.makaroni.bunjang.src.provider.ReviewProvider;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewProvider reviewProvider;

	@GetMapping("/stores/{storeIdx}")
	public ResponseEntity<List<ReviewSpecificView>> findAll(@PathVariable Long storeIdx,
															@RequestParam(defaultValue = "0") Integer start,
															@RequestParam(defaultValue = "10") Integer offset) {
		return ResponseEntity.ok(reviewProvider.findAllByStoreIdx(storeIdx,start, offset));
	}
}
