package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.review.dto.ReviewCommentDto;
import shop.makaroni.bunjang.src.domain.review.dto.ReviewSpecificDto;
import shop.makaroni.bunjang.src.domain.review.view.ReviewSimpleView;
import shop.makaroni.bunjang.src.domain.review.view.ReviewSpecificView;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static shop.makaroni.bunjang.src.domain.item.State.NOT_SAVED;
import static shop.makaroni.bunjang.src.domain.item.State.isAlreadySaved;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewProvider {

	private final ReviewDao reviewDao;
	private final UserDao userDao;

	public String getRating(Long storeIdx) {
		return reviewDao.getRating(storeIdx).orElseThrow(NoSuchElementException::new);
	}

	public List<ReviewSimpleView> getReviewInfo(Long storeIdx) {
		return reviewDao.getReviewInfo(storeIdx);
	}

	public List<ReviewSpecificView> findAllByStoreIdx(Long storeIdx, Integer start, Integer offset) {
		List<ReviewSpecificDto> reviews = reviewDao.findAllByStoreIdx(storeIdx, start, offset);

		return reviews.stream()
				.map(review -> ReviewSpecificView.of(review, getSellerName(review.getItemIdx()), getReviewCommentDto(review.getIdx())))
				.collect(Collectors.toList());
	}

	public void checkIfExist(Long userIdx, Long itemIdx) {
		String state = reviewDao.getReviewStatus(userIdx, itemIdx)
				.orElseGet(() -> NOT_SAVED.getState());
		isAlreadySaved(state);
	}

	private String getSellerName(Long itemIdx) {
		return userDao.getSellerNameByItemIdx(itemIdx);
	}

	private ReviewCommentDto getReviewCommentDto(Long reviewId) {
		return (ReviewCommentDto) reviewDao.findReviewCommentById(reviewId).orElseGet(ReviewCommentDto::mock);
	}
}
