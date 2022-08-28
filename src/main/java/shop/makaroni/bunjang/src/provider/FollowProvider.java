package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.FollowDao;
import shop.makaroni.bunjang.src.domain.follow.view.FollowersView;
import shop.makaroni.bunjang.src.domain.review.dto.FollowersDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowProvider {

	private final FollowDao followDao;

	public List<FollowersView> getFollowers(Long userIdx) {
		List<FollowersDto> dtos = followDao.getFollowers(userIdx);
		return dtos.stream()
				.map(FollowersView::of)
				.collect(Collectors.toList());
	}
}
