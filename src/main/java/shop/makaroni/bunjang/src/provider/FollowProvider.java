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
import shop.makaroni.bunjang.src.response.exception.AlreadyNotificationEx;
import shop.makaroni.bunjang.src.response.exception.AlreadySavedException;
import shop.makaroni.bunjang.src.response.exception.NotExistFollowEx;

import java.util.List;
import java.util.stream.Collectors;

import static shop.makaroni.bunjang.src.response.ErrorCode.ALREADY_NOTIFICATION_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.ALREADY_SAVED_FOLLOW_REVIEW;
import static shop.makaroni.bunjang.src.response.ErrorCode.NOT_EXIST_FOLLOW_EXCEPTION;

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
		isAlreadyFollow(userIdx, storeIdx);
	}

	private void isAlreadyFollow(Long userIdx, Long storeIdx) {
		if (alreadyExistFollow(userIdx, storeIdx)) {
			throw new AlreadySavedException(ALREADY_SAVED_FOLLOW_REVIEW.getMessages());
		}
	}

	public void isAlreadyNotification(Long userIdx, Long storeIdx) {
		notFollow(userIdx, storeIdx);
		isNotify(userIdx, storeIdx);
	}

	public void notFollow(Long userIdx, Long storeIdx) {
		if (alreadyNotFollow(userIdx, storeIdx)) {
			throw new NotExistFollowEx(NOT_EXIST_FOLLOW_EXCEPTION.getMessages());
		}
	}

	private void isNotify(Long userIdx, Long storeIdx) {
		if (alreadyNotification(userIdx, storeIdx)) {
			throw new AlreadyNotificationEx(ALREADY_NOTIFICATION_EXCEPTION.getMessages());
		}
	}

	private boolean alreadyNotFollow(Long userIdx, Long storeIdx) {
		return !followDao.alreadyExistFollow(userIdx, storeIdx);
	}

	private boolean alreadyNotification(Long userIdx, Long storeIdx) {
		return followDao.alreadyNotification(userIdx, storeIdx);
	}

	private Boolean alreadyExistFollow(Long userIdx, Long storeIdx) {
		return followDao.alreadyExistFollow(userIdx, storeIdx);
	}

}