package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.domain.item.State;
import shop.makaroni.bunjang.src.domain.review.dto.PostReviewRequest;
import shop.makaroni.bunjang.src.provider.ReviewProvider;
import shop.makaroni.bunjang.src.provider.UserProvider;
import shop.makaroni.bunjang.src.response.exception.AlreadyDeletedException;

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

		request.getImages()
				.forEach(image -> reviewDao.saveReviewImage(reviewIdx, image));
		return reviewIdx;
	}

	public void delete(Long reviewIdx) {

		String state = reviewDao.findReviewStatusById(reviewIdx).orElseThrow(NoSuchElementException::new);

		if (State.isAlreadyDeleted(state)) {
			throw new AlreadyDeletedException(ALREADY_DELETED_REVIEW_EXCEPTION.getMessages());
		}

		reviewDao.delete(reviewIdx);
		reviewDao.deleteReviewImagesByReviewIdx(reviewIdx);
	}
}
