package shop.makaroni.bunjang.src.domain.inquiry.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquirySimpleResponse {
	private String name;
	private String image;
	private String post;
	private String date;

	public InquirySimpleResponse(String name, String image, String post, String date) {
		this.name = name;
		this.image = image;
		this.post = post;
		this.date = date;
	}
}
