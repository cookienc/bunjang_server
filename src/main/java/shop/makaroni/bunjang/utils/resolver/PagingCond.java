package shop.makaroni.bunjang.utils.resolver;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PagingCond {
	public static final String ASC = "asc";
	private Integer start;
	private Integer offset;
	private String sortCond;

	@Builder
	public PagingCond(Integer start, Integer offset, String sortCond) {
		this.start = start;
		this.offset = offset;
		this.sortCond = sortCond;
	}

	public static PagingCond defaultValue() {
		return new PagingCond(0, 5, ASC);
	}

	public PagingCond validate() {
		return PagingCond.builder()
				.start(this.start == null ? 0 : this.start)
				.offset(this.offset == null ? 5 : this.offset)
				.sortCond(this.sortCond == null ? ASC : this.sortCond)
				.build();
	}
}
