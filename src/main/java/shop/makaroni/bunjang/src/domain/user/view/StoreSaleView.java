package shop.makaroni.bunjang.src.domain.user.view;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.item.Item;

import static shop.makaroni.bunjang.config.Constant.CANNOT_FIND_LOCATION;

@Getter
@NoArgsConstructor
public class StoreSaleView {
	String itemIdx;
	String itemName;
	String price;
	String location;
	String image;
	String time;

	@Builder
	public StoreSaleView(String itemIdx, String itemName, String price, String location, String image, String time) {
		this.itemIdx = itemIdx;
		this.itemName = itemName;
		this.price = price;
		this.location = location;
		this.image = image;
		this.time = time;
	}

	public static StoreSaleView of(Item item) {
		return StoreSaleView.builder()
				.itemIdx(String.valueOf(item.getIdx()))
				.itemName(item.getName())
				.price(String.valueOf(item.getPrice()))
				.location(item.getLocation() == null ? CANNOT_FIND_LOCATION.getMessages() : item.getLocation())
				.image(item.getImage())
				.time(item.getUpdatedAt())
				.build();
	}
}
