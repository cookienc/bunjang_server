package shop.makaroni.bunjang.src.domain.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreSearchDto {
	private Long storeIdx;
	private String storeName;
	private String storeImage;
	private LocalDateTime createdAt;
}
