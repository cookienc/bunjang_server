package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.domain.item.State;
import shop.makaroni.bunjang.src.domain.review.UpdateReviewRequest;
import shop.makaroni.bunjang.src.domain.review.dto.PostReviewRequest;
import shop.makaroni.bunjang.src.domain.review.dto.SaveReviewCommentRequest;
import shop.makaroni.bunjang.src.provider.ReviewProvider;
import shop.makaroni.bunjang.src.provider.UserProvider;
import shop.makaroni.bunjang.src.response.exception.AlreadyDeletedException;

import java.util.List;
import java.util.NoSuchElementException;

import static shop.makaroni.bunjang.src.response.ErrorCode.ALREADY_DELETED_REVIEW_EXCEPTION;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewDao reviewDao;

	private final ReviewProvider reviewProvider;
	private final UserProvider userProvider;

	public Long save(Long storeIdx, Long userIdx, PostReviewRequest request) {

		userProvider.checkPurchased(storeIdx, userIdx, request.getItemIdx());
		reviewProvider.checkIfExist(userIdx, request.getItemIdx());

		Long reviewIdx = reviewDao.save(request.getItemIdx(), userIdx, request.getPost(), request.getRating());
		saveReviewImages(reviewIdx, request.getImages());
		
		return reviewIdx;
	}

	public void updateReview(Long reviewIdx, UpdateReviewRequest request) {
		checkIfAlreadyDeleted(reviewIdx);

		modifyReview(reviewIdx, request);
	}

	public void delete(Long reviewIdx) {
		checkIfAlreadyDeleted(reviewIdx);

		reviewDao.delete(reviewIdx);
		reviewDao.deleteReviewImagesByReviewIdx(reviewIdx);
	}

	public Long saveReviewComment(Long reviewIdx, SaveReviewCommentRequest request) {
		checkIfAlreadyDeleted(reviewIdx);
		reviewDao.addReviewCommentOnParent(reviewIdx);
		return reviewDao.saveReviewComment(reviewIdx, request.getPost());
	}

	private void modifyReview(Long reviewIdx, UpdateReviewRequest request) {
		reviewDao.updateReview(reviewIdx, request);
		reviewDao.cleanDeleteReviewImages(reviewIdx);
		saveReviewImages(reviewIdx, request.getImages());
	}

	private void checkIfAlreadyDeleted(Long reviewIdx) {
		String state = reviewDao.findReviewStatusById(reviewIdx).orElseThrow(NoSuchElementException::new);

		if (State.isAlreadyDeleted(state)) {
			throw new AlreadyDeletedException(ALREADY_DELETED_REVIEW_EXCEPTION.getMessages());
		}
	}

	private void saveReviewImages(Long reviewIdx, List<String> request) {
		request.forEach(image -> reviewDao.saveReviewImage(reviewIdx, image));
	}
}
