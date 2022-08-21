package shop.makaroni.bunjang.src.provider;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.FollowDao;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.dao.WishListDao;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.MyStoreResponse;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserProvider {

	private final UserDao userDao;
	private final ReviewDao reviewDao;
	private final WishListDao wishListDao;
	private final FollowDao followDao;

	public MyStoreResponse getMyStore(Long userId) {
		User user = userDao.getMyStore(userId).orElseThrow(NoSuchElementException::new);
		Integer reviewCount = reviewDao.countStoreReview(userId);
		Integer wishListCount = wishListDao.countWishList(userId);
		Integer followerCount = followDao.countFollowers(userId);
		Integer followingcount =  followDao.countFollowings(userId);
		log.info("reviewCount = {}", reviewCount);
		log.info("user.image = {}", user.getStoreImage());

		return MyStoreResponse.of(user, reviewCount, wishListCount, followerCount, followingcount);
	}
}
