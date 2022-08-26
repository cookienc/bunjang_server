package shop.makaroni.bunjang.src.domain.inquiry.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class InquirySaveRequest {
	private Long parentIdx;

	@NotNull(message = "내용을 입력해주세요.")
	@NotBlank(message = "내용을 입력해주세요.")
	private String post;

	public InquirySaveRequest(Long parentIdx, String post) {
		this.parentIdx = parentIdx;
		this.post = post;
	}

	public void checkParentIdx() {
		if (this.parentIdx == null) {
			this.parentIdx = 0L;
		}
	}
}
