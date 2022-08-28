package shop.makaroni.bunjang.src.domain.follow.view;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.review.dto.FollowersDto;

@Getter
@NoArgsConstructor
public class FollowersView {
	private String storeIdx;
	private String storeName;
	private String storeImage;
	private String followers;
	private String items;

	@Builder
	public FollowersView(String storeIdx, String storeName, String storeImage, String followers, String items) {
		this.storeIdx = storeIdx;
		this.storeName = storeName;
		this.storeImage = storeImage;
		this.followers = followers;
		this.items = items;
	}

	public static FollowersView of(FollowersDto dto) {
		return FollowersView.builder()
				.storeIdx(dto.getStoreIdx())
				.storeName(dto.getStoreName())
				.storeImage(dto.getStoreImage())
				.followers(dto.getFollowers())
				.items(dto.getItems())
				.build();
	}
}
