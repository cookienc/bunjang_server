package shop.makaroni.bunjang.src.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.item.Item;

import static shop.makaroni.bunjang.config.Constant.CANNOT_FIND_LOCATION;

@Getter
@NoArgsConstructor
public class StoreSaleResponse {
	Long itemIdx;
	String itemName;
	Long price;
	String location;
	String image;
	String createdAt;

	@Builder
	public StoreSaleResponse(Long itemIdx, String itemName, Long price, String location, String image, String createdAt) {
		this.itemIdx = itemIdx;
		this.itemName = itemName;
		this.price = price;
		this.location = location;
		this.image = image;
		this.createdAt = createdAt;
	}

	public static StoreSaleResponse of(Item item) {
		return StoreSaleResponse.builder()
				.itemIdx(item.getIdx())
				.itemName(item.getName())
				.price(item.getPrice())
				.location(item.getLocation() == null ? CANNOT_FIND_LOCATION.getMessages() : item.getLocation())
				.image(item.getImage())
				.createdAt(item.getUpdatedAt())
				.build();
	}
}
