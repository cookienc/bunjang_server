package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.user.User;

@Getter
@NoArgsConstructor
public class MyStoreResponse {
	private Long storeId;
	private String storeName;
	private Boolean isCertificated;
	private String storeImage;
	private Integer wishLists;
	private Integer reviews;
	private Integer followers;
	private Integer followings;

	@Builder

	public MyStoreResponse(Long storeId, String storeName, Boolean isCertificated, String storeImage, Integer wishLists, Integer reviews, Integer followers, Integer followings) {
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
				.storeId(user.getIdx())
				.storeName(user.getStoreName())
				.isCertificated(user.getIsCertificated())
				.wishLists(wishListCount)
				.followers(followerCount)
				.followings(followingcount)
				.build();
	}
}
