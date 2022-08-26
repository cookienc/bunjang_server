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
import shop.makaroni.bunjang.src.domain.review.dto.PurchasedItemsView;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PurchasedItemsDto;
import shop.makaroni.bunjang.src.domain.user.dto.StoreSearchDto;
import shop.makaroni.bunjang.src.domain.user.view.MyStoreResponse;
import shop.makaroni.bunjang.src.domain.user.view.StoreInfoView;
import shop.makaroni.bunjang.src.domain.user.view.StoreSaleView;
import shop.makaroni.bunjang.src.domain.user.view.StoreSearchView;
import shop.makaroni.bunjang.src.response.exception.CannotFindPurchasedItem;
import shop.makaroni.bunjang.src.response.exception.DontPurchaseItemEx;
import shop.makaroni.bunjang.src.response.exception.DuplicateLoginIdEx;
import shop.makaroni.bunjang.utils.resolver.PagingCond;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static shop.makaroni.bunjang.src.response.ErrorCode.CANNOT_FIND_PURCHASED_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.DONT_PURCHASE_ITEM_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.DUPLICATE_LOGIN_ID_EXCEPTION;

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
		Integer followerCount = countFollowers(userIdx);
		Integer followingcount =  followDao.countMyFollowings(userIdx);
		String rating = reviewProvider.getRating(userIdx);

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

		throw new DuplicateLoginIdEx(DUPLICATE_LOGIN_ID_EXCEPTION.getMessages());
	}

	public StoreInfoView getStoreById(Long storeIdx) {

		User user = findById(storeIdx);
		Integer reviewCount = reviewDao.countStoreReview(storeIdx);
		Integer wishListCount = wishListDao.countMyWishList(storeIdx);
		Integer followerCount = countFollowers(storeIdx);
		Integer followingCount = followDao.countMyFollowings(storeIdx);
		String soldCount = userDao.getSoldCount(storeIdx);
		String rating = reviewProvider.getRating(storeIdx);

		return StoreInfoView.of(user, rating, reviewCount, wishListCount, followerCount, followingCount, soldCount,
				getMyStoreItem(storeIdx, State.SELLING.getState(), PagingCond.defaultValue()),
				reviewProvider.getReviewInfo(storeIdx), inquiryProvider.getInquiryInfo(storeIdx));
	}

	public List<StoreSearchView> searchStoreByName(String name) {
		List<StoreSearchDto> results = userDao.searchStoreByName(name);
		return results.stream()
				.map(result -> StoreSearchView.of(result, countFollowers(result.getStoreIdx()), countStoreItems(result.getStoreIdx())))
				.sorted(Comparator.comparing(StoreSearchView::getFollowers).reversed()
						.thenComparing(StoreSearchView::getItems).reversed())
				.collect(Collectors.toList());
	}

	public void checkPurchased(Long storeIdx, Long userIdx, Long itemIdx) {
		userDao.findItemWithUserIdxItemIdx(storeIdx, userIdx, itemIdx)
				.orElseThrow(() -> new CannotFindPurchasedItem(CANNOT_FIND_PURCHASED_EXCEPTION.getMessages()));
	}

	public List<PurchasedItemsView> getPurchasedItems(Long storeIdx, Long userIdx) {
		List<PurchasedItemsDto> purchasedItems = userDao.findPurchasedItems(storeIdx, userIdx);

		isItEmpty(purchasedItems);

		return purchasedItems.stream()
				.map(PurchasedItemsView::of)
				.collect(Collectors.toList());
	}

	private Integer countStoreItems(Long storeIdx) {
		return userDao.countAllItems(storeIdx);
	}

	private Integer countFollowers(Long storeIdx) {
		return followDao.countMyFollowers(storeIdx);
	}

	private void isItEmpty(List<PurchasedItemsDto> purchasedItems) {
		if (purchasedItems.isEmpty()) {
			throw new DontPurchaseItemEx(DONT_PURCHASE_ITEM_EXCEPTION.getMessages());
		}
	}
}
