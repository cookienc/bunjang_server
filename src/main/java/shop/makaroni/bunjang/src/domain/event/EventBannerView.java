package shop.makaroni.bunjang.src.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.event.dto.EventBannerDto;

@Getter
@NoArgsConstructor
public class EventBannerView {
	private String eventIdx;
	private String name;
	private String image;

	@Builder
	public EventBannerView(String eventIdx, String name, String image) {
		this.eventIdx = eventIdx;
		this.name = name;
		this.image = image;
	}

	public static EventBannerView of(EventBannerDto dto) {
		return EventBannerView.builder()
				.eventIdx(dto.getEventIdx())
				.name(dto.getName())
				.image(dto.getImage())
				.build();
	}
}
