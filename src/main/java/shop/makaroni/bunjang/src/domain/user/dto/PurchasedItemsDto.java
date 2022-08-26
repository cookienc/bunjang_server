package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchasedItemsDto {
	private String itemIdx;
	private String itemName;
	private String itemImage;
}
