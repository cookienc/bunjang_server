package shop.makaroni.bunjang.src.provider;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.FollowDao;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.ReviewDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.dao.WishListDao;
import shop.makaroni.bunjang.src.domain.item.Item;
import shop.makaroni.bunjang.src.domain.user.StoreSaleResponse;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.MyStoreResponse;

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
	private final ItemDao itemDao;

	public MyStoreResponse getMyStore(Long userId) {
		User user = userDao.getMyStore(userId).orElseThrow(NoSuchElementException::new);
		Integer reviewCount = reviewDao.countStoreReview(userId);
		Integer wishListCount = wishListDao.countMyWishList(userId);
		Integer followerCount = followDao.countMyFollowers(userId);
		Integer followingcount =  followDao.countMyFollowings(userId);

		return MyStoreResponse.of(user, reviewCount, wishListCount, followerCount, followingcount);
	}

	public List<StoreSaleResponse> getMyStoreItem(Long userId, String condition) {
		List<Item> item = itemDao.getMyStoreItem(userId, condition);
		return item.stream()
				.map(StoreSaleResponse::of)
				.collect(Collectors.toList());
	}
}
