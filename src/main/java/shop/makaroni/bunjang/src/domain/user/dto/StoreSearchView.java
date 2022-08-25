package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shop.makaroni.bunjang.src.domain.user.StoreSearchDto;

@Getter
public class StoreSearchView {
	private String storeIdx;
	private String storeName;
	private String storeImage;
	private String followers;
	private String items;

	@Builder
	public StoreSearchView(String storeIdx, String storeName, String storeImage, String followers, String items) {
		this.storeIdx = storeIdx;
		this.storeName = storeName;
		this.storeImage = storeImage;
		this.followers = followers;
		this.items = items;
	}

	public static StoreSearchView of(StoreSearchDto dto, Integer followers, Integer storeItems) {
		return StoreSearchView.builder()
				.storeIdx(String.valueOf(dto.getStoreIdx()))
				.storeName(dto.getStoreName())
				.storeImage(dto.getStoreImage())
				.followers(String.valueOf(followers))
				.items(String.valueOf(storeItems))
				.build();
	}
}
