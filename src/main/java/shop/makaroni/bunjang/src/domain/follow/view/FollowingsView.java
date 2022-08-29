package shop.makaroni.bunjang.src.domain.follow.view;

import lombok.Builder;
import lombok.Getter;
import shop.makaroni.bunjang.src.domain.follow.dto.FollowingsDto;

import java.util.List;

@Getter
public class FollowingsView {
	private String storeIdx;
	private String storeImage;
	private String followers;
	private String items;
	private List<ItemFollowingView> itemList;

	@Builder
	public FollowingsView(String storeIdx, String storeImage, String followers, String items, List<ItemFollowingView> itemList) {
		this.storeIdx = storeIdx;
		this.storeImage = storeImage;
		this.followers = followers;
		this.items = items;
		this.itemList = itemList;
	}

	public static FollowingsView of(FollowingsDto dto, List<ItemFollowingView> followings) {
		return FollowingsView.builder()
				.storeIdx(String.valueOf(dto.getStoreIdx()))
				.storeImage(dto.getStoreImage())
				.followers(dto.getFollowers())
				.items(dto.getItems())
				.itemList(followings)
				.build();
	}
}
