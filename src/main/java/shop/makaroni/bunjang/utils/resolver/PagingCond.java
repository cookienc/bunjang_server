package shop.makaroni.bunjang.utils.resolver;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PagingCond {
	public static final String ASC = "asc";
	private Integer start;
	private Integer offset;
	private String dateSort;

	@Builder
	public PagingCond(Integer start, Integer offset, String dateSort) {
		this.start = start;
		this.offset = offset;
		this.dateSort = dateSort;
	}

	public static PagingCond defaultValue() {
		return new PagingCond(0, 5, ASC);
	}

	public PagingCond validate() {
		return PagingCond.builder()
				.start(this.start == null ? 0 : this.start)
				.offset(this.offset == null ? 10 : this.offset)
				.dateSort(this.dateSort == null ? ASC : this.dateSort)
				.build();
	}
}
