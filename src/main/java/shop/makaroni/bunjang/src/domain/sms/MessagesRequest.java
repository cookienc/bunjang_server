package shop.makaroni.bunjang.src.domain.sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessagesRequest {
	private String to;
	private String content;

	public MessagesRequest(String to, String content) {
		this.to = to;
		this.content = content;
	}
}
