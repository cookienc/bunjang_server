package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.domain.review.dto.ReviewAllResponse;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewProvider {

	private final ReviewDao reviewDao;

	public String getRating(Long storeIdx) {
		return reviewDao.getRating(storeIdx);
	}

	public List<ReviewAllResponse> getReviewInfo(Long storeIdx) {
		return reviewDao.getReviewInfo(storeIdx);
	}
}
