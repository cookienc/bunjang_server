package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.user.User;

@Getter
@NoArgsConstructor
public class MyStoreResponse {
	private String storeId;
	private String storeName;
	private Boolean isCertificated;
	private String storeImage;
	private String wishLists;
	private String reviews;
	private String followers;
	private String followings;

	@Builder

	public MyStoreResponse(String storeId, String storeName, Boolean isCertificated, String storeImage, String wishLists, String reviews, String followers, String followings) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.isCertificated = isCertificated;
		this.storeImage = storeImage;
		this.wishLists = wishLists;
		this.reviews = reviews;
		this.followers = followers;
		this.followings = followings;
	}

	public static MyStoreResponse of(User user, Integer reviewCount, Integer wishListCount, Integer followerCount, Integer followingcount) {
		return MyStoreResponse.builder()
				.storeId(String.valueOf(user.getIdx()))
				.storeName(user.getStoreName())
				.storeImage(user.getStoreImage())
				.isCertificated(user.getIsCertificated())
				.wishLists(String.valueOf(wishListCount))
				.reviews(String.valueOf(reviewCount))
				.followers(String.valueOf(followerCount))
				.followings(String.valueOf(followingcount))
				.build();
	}
}
