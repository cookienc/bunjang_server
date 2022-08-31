package shop.makaroni.bunjang.src.domain.sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SmsRequest {
	private String type;
	private String contentType;
	private String countryCode;
	private String from;
	private String content;
	private List<MessagesRequest> messages;

	public SmsRequest(String type, String contentType, String countryCode, String from, String content, List<MessagesRequest> messages) {
		this.type = type;
		this.contentType = contentType;
		this.countryCode = countryCode;
		this.from = from;
		this.content = content;
		this.messages = messages;
	}
}
