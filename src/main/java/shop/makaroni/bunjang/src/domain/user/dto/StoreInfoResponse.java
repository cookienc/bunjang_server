package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shop.makaroni.bunjang.src.domain.review.dto.ReviewAllResponse;
import shop.makaroni.bunjang.src.domain.user.User;

import java.util.List;

@Getter
public class StoreInfoResponse {
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
	private List<StoreSaleResponse> itemsResponses;
	private List<ReviewAllResponse> reviewsResponses;

	@Builder
	public StoreInfoResponse(String storeId, String storeName, Boolean isCertificated, String storeImage, String rating, String wishLists, String reviews, String followers, String followings, String soldCount, String contactStart, String contactEnd, String precaution, String policy, String description, String openDate, String hit, List<StoreSaleResponse> itemsResponses, List<ReviewAllResponse> reviewsResponses) {
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
	}

	public static StoreInfoResponse of(User user, String rating, Integer reviewCount, Integer wishListCount, Integer followerCount, Integer followingCount, String soldCount, List<StoreSaleResponse> itemSalesResponse, List<ReviewAllResponse> reviewInfo) {
		return StoreInfoResponse.builder()
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
				.build();
	}
}
