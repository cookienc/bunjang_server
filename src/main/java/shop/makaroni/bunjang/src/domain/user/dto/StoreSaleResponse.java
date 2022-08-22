package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.item.Item;
import shop.makaroni.bunjang.utils.Formatter;

import static shop.makaroni.bunjang.config.Constant.CANNOT_FIND_LOCATION;

@Getter
@NoArgsConstructor
public class StoreSaleResponse {
	Long itemIdx;
	String itemName;
	String price;
	String location;
	String image;
	String time;

	@Builder
	public StoreSaleResponse(Long itemIdx, String itemName, String price, String location, String image, String time) {
		this.itemIdx = itemIdx;
		this.itemName = itemName;
		this.price = price;
		this.location = location;
		this.image = image;
		this.time = time;
	}

	public static StoreSaleResponse of(Item item) {
		return StoreSaleResponse.builder()
				.itemIdx(item.getIdx())
				.itemName(item.getName())
				.price(Formatter.changeWon(item.getPrice()))
				.location(item.getLocation() == null ? CANNOT_FIND_LOCATION.getMessages() : item.getLocation())
				.image(item.getImage())
				.time(item.getUpdatedAt())
				.build();
	}
}
