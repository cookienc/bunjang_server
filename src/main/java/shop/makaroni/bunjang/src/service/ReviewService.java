package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.domain.review.dto.PostReviewRequest;
import shop.makaroni.bunjang.src.provider.UserProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewDao reviewDao;
	private final UserProvider userProvider;

	public Long save(Long storeIdx, Long userIdx, PostReviewRequest request) {

		userProvider.checkPurchased(storeIdx, userIdx, request.getItemIdx());

		Long reviewIdx = reviewDao.save(request.getItemIdx(), userIdx, request.getPost(), request.getRating());
		System.out.println("reviewIdx : "+ reviewIdx);
		request.getImages().stream()
				.forEach(image -> reviewDao.saveReviewImage(reviewIdx, image));
		return reviewIdx;
	}
}
