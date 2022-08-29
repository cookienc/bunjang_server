package shop.makaroni.bunjang.src.domain.follow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingsDto {
	private Long storeIdx;
	private String storeImage;
	private String followers;
	private String items;
}
