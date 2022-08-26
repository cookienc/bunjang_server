package shop.makaroni.bunjang.src.domain.user.view;

import lombok.Builder;
import lombok.Getter;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleView;
import shop.makaroni.bunjang.src.domain.review.view.ReviewSimpleView;
import shop.makaroni.bunjang.src.domain.user.User;

import java.util.List;

@Getter
public class StoreInfoView {
	private String storeId;
	private String storeName;
	private Boolean isCertificated;
	private String storeImage;
	private String rating;
	private String wishLists;
	private String reviews;
	private String followers;
	private String followings;
	private String soldCount;
	private String contactStart;
	private String contactEnd;
	private String precaution;
	private String policy;
	private String description;
	private String openDate;
	private String hit;
	private List<StoreSaleView> itemsResponses;
	private List<ReviewSimpleView> reviewsResponses;
	private List<InquirySimpleView> inquiryResponses;

	@Builder
	public StoreInfoView(String storeId, String storeName, Boolean isCertificated, String storeImage, String rating, String wishLists, String reviews, String followers, String followings, String soldCount, String contactStart, String contactEnd, String precaution, String policy, String description, String openDate, String hit, List<StoreSaleView> itemsResponses, List<ReviewSimpleView> reviewsResponses, List<InquirySimpleView> inquiryResponses) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.isCertificated = isCertificated;
		this.storeImage = storeImage;
		this.rating = rating;
		this.wishLists = wishLists;
		this.reviews = reviews;
		this.followers = followers;
		this.followings = followings;
		this.soldCount = soldCount;
		this.contactStart = contactStart;
		this.contactEnd = contactEnd;
		this.precaution = precaution;
		this.policy = policy;
		this.description = description;
		this.openDate = openDate;
		this.hit = hit;
		this.itemsResponses = itemsResponses;
		this.reviewsResponses = reviewsResponses;
		this.inquiryResponses = inquiryResponses;
	}

	public static StoreInfoView of(User user, String rating, Integer reviewCount, Integer wishListCount, Integer followerCount, Integer followingCount, String soldCount, List<StoreSaleView> itemSalesResponse, List<ReviewSimpleView> reviewInfo, List<InquirySimpleView> inquiryInfo) {
		return StoreInfoView.builder()
				.storeId(String.valueOf(user.getIdx()))
				.storeName(user.getStoreName())
				.isCertificated(user.getIsCertificated())
				.storeImage(user.getStoreImage())
				.rating(rating)
				.wishLists(String.valueOf(wishListCount))
				.reviews(String.valueOf(reviewCount))
				.followers(String.valueOf(followerCount))
				.followings(String.valueOf(followingCount))
				.soldCount(soldCount)
				.contactStart(user.getContactStart())
				.contactEnd(user.getContactEnd())
				.precaution(user.getPrecaution())
				.policy(user.getPolicy())
				.description(user.getDescription())
				.openDate(user.getOpenDate())
				.hit(user.getHit())
				.itemsResponses(itemSalesResponse)
				.reviewsResponses(reviewInfo)
				.inquiryResponses(inquiryInfo)
				.build();
	}
}
