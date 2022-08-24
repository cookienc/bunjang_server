package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.ReviewDao;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewProvider {

	private ReviewDao reviewDao;

	public String getRating(Long storeIdx) {
		return reviewDao.getRating(storeIdx);
	}
}
