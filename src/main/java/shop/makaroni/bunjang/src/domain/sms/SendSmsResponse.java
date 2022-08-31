package shop.makaroni.bunjang.src.domain.sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SendSmsResponse {
	private String statusCode;
	private String statusName;
	private String requestId;
	private LocalDateTime requestTime;

	public SendSmsResponse(String statusCode, String statusName, String requestId, LocalDateTime requestTime) {
		this.statusCode = statusCode;
		this.statusName = statusName;
		this.requestId = requestId;
		this.requestTime = requestTime;
	}
}
