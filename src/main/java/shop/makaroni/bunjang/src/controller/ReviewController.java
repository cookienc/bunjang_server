package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.review.UpdateReviewRequest;
import shop.makaroni.bunjang.src.domain.review.dto.PostReviewRequest;
import shop.makaroni.bunjang.src.domain.review.dto.SaveReviewCommentRequest;
import shop.makaroni.bunjang.src.domain.review.dto.UpdateReviewCommentRequest;
import shop.makaroni.bunjang.src.domain.review.view.PurchasedItemsView;
import shop.makaroni.bunjang.src.domain.review.view.ReviewSpecificView;
import shop.makaroni.bunjang.src.domain.review.view.SingleReviewResponse;
import shop.makaroni.bunjang.src.provider.ReviewProvider;
import shop.makaroni.bunjang.src.provider.UserProvider;
import shop.makaroni.bunjang.src.response.ResponseInfo;
import shop.makaroni.bunjang.src.response.SuccessStatus;
import shop.makaroni.bunjang.src.service.ReviewService;
import shop.makaroni.bunjang.utils.resolver.Login;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static shop.makaroni.bunjang.src.response.SuccessStatus.DELETE_REVIEW_COMMENT_SUCCESS;
import static shop.makaroni.bunjang.src.response.SuccessStatus.SAVE_REVIEW_COMMENT_SUCCESS;
import static shop.makaroni.bunjang.src.response.SuccessStatus.SAVE_SUCCESS;
import static shop.makaroni.bunjang.src.response.SuccessStatus.UPDATE_REVIEW_COMMENT_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewProvider reviewProvider;
	private final ReviewService reviewService;
	private final UserProvider userProvider;

	@GetMapping("/stores/{storeIdx}")
	public ResponseEntity<List<ReviewSpecificView>> findAll(@PathVariable Long storeIdx,
															@RequestParam(defaultValue = "0") Integer start,
															@RequestParam(defaultValue = "10") Integer offset) {
		return ResponseEntity.ok(reviewProvider.findAllByStoreIdx(storeIdx, start, offset));
	}

	@GetMapping("/stores/{storeIdx}/users")
	public ResponseEntity<List<PurchasedItemsView>> getPurchasedItems(@PathVariable Long storeIdx, @Login Long userIdx) {
		return ResponseEntity.ok(userProvider.getPurchasedItems(storeIdx, userIdx));
	}

	@PostMapping("/stores/{storeIdx}/users")
	public ResponseEntity<ResponseInfo> save(@PathVariable Long storeIdx, @Login Long userIdx,
											 @Valid @RequestBody PostReviewRequest request) {
		Long reviewId = reviewService.save(storeIdx, userIdx, request);
		String uri = "/reviews/" + reviewId;
		return ResponseEntity.created(URI.create(uri)).body(ResponseInfo.of(SAVE_SUCCESS));
	}

	@PatchMapping("/d/{reviewIdx}")
	public ResponseEntity<ResponseInfo> delete(@PathVariable Long reviewIdx) {
		reviewService.delete(reviewIdx);
		return ResponseEntity.ok(ResponseInfo.of(SuccessStatus.DELETE_REVIEW_SUCCESS));
	}

	@PatchMapping("/{reviewIdx}")
	public ResponseEntity<ResponseInfo> updateReview(@PathVariable Long reviewIdx, @Valid @RequestBody UpdateReviewRequest request) {
		reviewService.updateReview(reviewIdx, request);
		return ResponseEntity.ok(ResponseInfo.of(SuccessStatus.UPDATE_REVIEW_SUCCESS));
	}

	@GetMapping("/{reviewIdx}")
	public ResponseEntity<SingleReviewResponse> getReviewById(@PathVariable Long reviewIdx) {
		return ResponseEntity.ok(reviewProvider.getReviewById(reviewIdx));
	}

	@PostMapping("/{reviewIdx}/comments")
	public ResponseEntity<ResponseInfo> saveReviewComment(@PathVariable Long reviewIdx,
														  @Valid @RequestBody SaveReviewCommentRequest request) {
		Long commentId = reviewService.saveReviewComment(reviewIdx, request);
		String uri = "/reviews/" + reviewIdx + "/comments/" + commentId;
		return ResponseEntity.created(URI.create(uri)).body(ResponseInfo.of(SAVE_REVIEW_COMMENT_SUCCESS));
	}

	@PatchMapping("/{reviewIdx}/comments/d/{commentIdx}")
	public ResponseEntity<ResponseInfo> deleteComment(@PathVariable Long reviewIdx, @PathVariable Long commentIdx) {
		reviewService.deleteComment(reviewIdx, commentIdx);
		return ResponseEntity.ok(ResponseInfo.of(DELETE_REVIEW_COMMENT_SUCCESS));
	}

	@PatchMapping("/{reviewIdx}/comments/{commentIdx}")
	public ResponseEntity<ResponseInfo> updateComment(@PathVariable Long reviewIdx, @PathVariable Long commentIdx,
													  @Valid @RequestBody UpdateReviewCommentRequest request) {
		reviewService.updateComment(reviewIdx, commentIdx, request);
		return ResponseEntity.ok(ResponseInfo.of(UPDATE_REVIEW_COMMENT_SUCCESS));
	}

}
