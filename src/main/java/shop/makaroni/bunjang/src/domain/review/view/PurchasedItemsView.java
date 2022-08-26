package shop.makaroni.bunjang.src.domain.review.view;

import lombok.Builder;
import lombok.Getter;
import shop.makaroni.bunjang.src.domain.user.dto.PurchasedItemsDto;

@Getter
public class PurchasedItemsView {
	private String itemIdx;
	private String itemName;
	private String itemImage;

	@Builder
	public PurchasedItemsView(String itemIdx, String itemName, String itemImage) {
		this.itemIdx = itemIdx;
		this.itemName = itemName;
		this.itemImage = itemImage;
	}

	public static PurchasedItemsView of(PurchasedItemsDto purchasedItemsDto) {
		return PurchasedItemsView.builder()
				.itemIdx(purchasedItemsDto.getItemIdx())
				.itemName(purchasedItemsDto.getItemName())
				.itemImage(purchasedItemsDto.getItemImage())
				.build();
	}
}
