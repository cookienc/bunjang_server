package shop.makaroni.bunjang.src.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
	private Long idx;
	private Long sellerIdx;
	private String name;
	private String category;
	private Integer brandIdx;
	private Long price;
	private String content;
	private Integer stock;
	private Boolean delivery;
	private Boolean isNew;
	private Boolean exchange;
	private Boolean inspection;
	private Boolean safePay;
	private Integer hit;

	private String image;

	private String location;
	private String createdAt;
	private String updatedAt;
	private Character status;

	public Item(Long idx, Long sellerIdx, String name, String category, Integer brandIdx, Long price, String content, Integer stock, Boolean delivery, Boolean isNew, Boolean exchange, Boolean inspection, Boolean safePay, Integer hit, String image, String location, String createdAt, String updatedAt, Character status) {
		this.idx = idx;
		this.sellerIdx = sellerIdx;
		this.name = name;
		this.category = category;
		this.brandIdx = brandIdx;
		this.price = price;
		this.content = content;
		this.stock = stock;
		this.delivery = delivery;
		this.isNew = isNew;
		this.exchange = exchange;
		this.inspection = inspection;
		this.safePay = safePay;
		this.hit = hit;
		this.image = image;
		this.location = location;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}
}
