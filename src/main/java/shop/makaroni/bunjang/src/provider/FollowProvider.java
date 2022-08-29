package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.FollowDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.follow.dto.FollowersDto;
import shop.makaroni.bunjang.src.domain.follow.dto.FollowingsDto;
import shop.makaroni.bunjang.src.domain.follow.view.FollowersView;
import shop.makaroni.bunjang.src.domain.follow.view.FollowingsView;
import shop.makaroni.bunjang.src.response.exception.AlreadySavedException;

import java.util.List;
import java.util.stream.Collectors;

import static shop.makaroni.bunjang.src.response.ErrorCode.ALREADY_SAVED_REVIEW;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowProvider {

	private final FollowDao followDao;
	private final UserDao userDao;

	public List<FollowersView> getFollowers(Long userIdx) {
		List<FollowersDto> dtos = followDao.getFollowers(userIdx);
		return dtos.stream()
				.map(FollowersView::of)
				.collect(Collectors.toList());
	}

	public List<FollowingsView> getFollowings(Long userIdx) {
		List<FollowingsDto> dtos = followDao.getFollowings(userIdx);

		return dtos.stream()
				.map(dto -> FollowingsView.of(dto, userDao.findItemAndItemImages(dto.getStoreIdx())))
				.collect(Collectors.toList());
	}

	public void isAlreadyExist(Long userIdx, Long storeIdx) {
		if (followDao.alreadyExistFollow(userIdx, storeIdx)) {
			throw new AlreadySavedException(ALREADY_SAVED_REVIEW.getMessages());
		}
	}
}