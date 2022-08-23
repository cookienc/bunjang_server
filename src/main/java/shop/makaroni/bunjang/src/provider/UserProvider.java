package shop.makaroni.bunjang.src.provider;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.FollowDao;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.dao.WishListDao;
import shop.makaroni.bunjang.src.domain.item.Item;
import shop.makaroni.bunjang.src.domain.item.State;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.MyStoreResponse;
import shop.makaroni.bunjang.src.domain.user.dto.StoreSaleResponse;
import shop.makaroni.bunjang.utils.resolver.PagingCond;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserProvider {

	private final UserDao userDao;
	private final ReviewDao reviewDao;
	private final WishListDao wishListDao;
	private final FollowDao followDao;

	public MyStoreResponse getMyStore(Long userIdx) {

		User user = findById(userIdx);
		Integer reviewCount = reviewDao.countStoreReview(userIdx);
		Integer wishListCount = wishListDao.countMyWishList(userIdx);
		Integer followerCount = followDao.countMyFollowers(userIdx);
		Integer followingcount =  followDao.countMyFollowings(userIdx);

		return MyStoreResponse.of(user, reviewCount, wishListCount, followerCount, followingcount, getMyStoreItem(userIdx, State.SELLING.getState(), PagingCond.defaultValue()));
	}

	public List<StoreSaleResponse> getMyStoreItem(Long userIdx, String condition, PagingCond pagingCond) {
		findById(userIdx);
		List<Item> item = userDao.getMyStoreItem(userIdx, condition, pagingCond);
		return item.stream()
				.map(StoreSaleResponse::of)
				.collect(Collectors.toList());
	}

	public List<StoreSaleResponse> searchStoreItemByName(Long userIdx, String itemName, String condition, PagingCond pagingCond) {
		findById(userIdx);
		List<Item> item = userDao.searchStoreItemByName(userIdx, itemName, condition, pagingCond);
		return item.stream()
				.map(StoreSaleResponse::of)
				.collect(Collectors.toList());
	}

	public User findById(Long userIdx) {
		User user = userDao.findById(userIdx).orElseThrow(NoSuchElementException::new);
		user.validate();
		return user;
	}

	public User findByLoginId(String loginId) {
		User user = userDao.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);
		user.checkDuplicateLoginId(loginId);
		return user;
	}
}
