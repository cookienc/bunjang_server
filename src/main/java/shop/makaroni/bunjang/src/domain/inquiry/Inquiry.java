package shop.makaroni.bunjang.src.domain.inquiry;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Inquiry {
	private Long idx;
	private Long storeIdx;
	private Long userIdx;
	private Long parentIdx;
	private String post;

	@Builder
	public Inquiry(Long idx, Long storeIdx, Long userIdx, Long parentIdx, String post) {
		this.idx = idx;
		this.storeIdx = storeIdx;
		this.userIdx = userIdx;
		this.parentIdx = parentIdx;
		this.post = post;
	}

	public void changeIdx(long key) {
		this.idx = key;
	}
}
