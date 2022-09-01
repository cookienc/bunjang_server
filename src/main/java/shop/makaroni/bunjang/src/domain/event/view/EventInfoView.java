package shop.makaroni.bunjang.src.domain.event.view;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.makaroni.bunjang.src.domain.event.dto.EventInfoDto;

@Getter
@NoArgsConstructor
public class EventInfoView {
	private String infoIdx;
	private String image;
	private String title;
	private String post;

	@Builder
	public EventInfoView(String infoIdx, String image, String title, String post) {
		this.infoIdx = infoIdx;
		this.image = image;
		this.title = title;
		this.post = post;
	}

	public static EventInfoView of(EventInfoDto dto) {
		return EventInfoView.builder()
				.infoIdx(dto.getInfoIdx())
				.title(dto.getTitle())
				.image(dto.getImage())
				.post(dto.getPost())
				.build();
	}
}
