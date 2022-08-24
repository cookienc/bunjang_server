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
import shop.makaroni.bunjang.src.domain.user.dto.StoreInfoView;
import shop.makaroni.bunjang.src.domain.user.dto.StoreSaleView;
import shop.makaroni.bunjang.src.response.ErrorCode;
import shop.makaroni.bunjang.src.response.exception.DuplicateLoginIdEx;
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
	private final ReviewProvider reviewProvider;
	private final InquiryProvider inquiryProvider;

	public MyStoreResponse getMyStore(Long userIdx) {

		User user = findById(userIdx);
		Integer reviewCount = reviewDao.countStoreReview(userIdx);
		Integer wishListCount = wishListDao.countMyWishList(userIdx);
		Integer followerCount = followDao.countMyFollowers(userIdx);
		Integer followingcount =  followDao.countMyFollowings(userIdx);
		String rating = reviewDao.getRating(userIdx);

		return MyStoreResponse.of(user, reviewCount, wishListCount, followerCount, followingcount, rating,
				getMyStoreItem(userIdx, State.SELLING.getState(), PagingCond.defaultValue()));
	}

	public List<StoreSaleView> getMyStoreItem(Long userIdx, String condition, PagingCond pagingCond) {
		findById(userIdx);
		List<Item> item = userDao.getMyStoreItem(userIdx, condition, pagingCond);
		return item.stream()
				.map(StoreSaleView::of)
				.collect(Collectors.toList());
	}

	public List<StoreSaleView> searchStoreItemByName(Long userIdx, String itemName, String condition, PagingCond pagingCond) {
		findById(userIdx);
		List<Item> item = userDao.searchStoreItemByName(userIdx, itemName, condition, pagingCond);
		return item.stream()
				.map(StoreSaleView::of)
				.collect(Collectors.toList());
	}

	public User findById(Long userIdx) {
		User user = userDao.findById(userIdx).orElseThrow(NoSuchElementException::new);
		user.validate();
		return user;
	}

	public void checkDuplicateLoginId(String loginId) {
		boolean isNotDuplicate = userDao.findByLoginId(loginId).isEmpty();

		if (isNotDuplicate) {
			return;
		};

		throw new DuplicateLoginIdEx(ErrorCode.DUPLICATE_LOGIN_ID_EXCEPTION.getMessages());
	}

	public StoreInfoView getStoreById(Long storeIdx) {

		User user = findById(storeIdx);
		Integer reviewCount = reviewDao.countStoreReview(storeIdx);
		Integer wishListCount = wishListDao.countMyWishList(storeIdx);
		Integer followerCount = followDao.countMyFollowers(storeIdx);
		Integer followingCount = followDao.countMyFollowings(storeIdx);
		String soldCount = userDao.getSoldCount(storeIdx);
		String rating = reviewDao.getRating(storeIdx);

		return StoreInfoView.of(user, rating, reviewCount, wishListCount, followerCount, followingCount, soldCount,
				getMyStoreItem(storeIdx, State.SELLING.getState(), PagingCond.defaultValue()),
				reviewProvider.getReviewInfo(storeIdx), inquiryProvider.getInquiryInfo(storeIdx));
	}
}
