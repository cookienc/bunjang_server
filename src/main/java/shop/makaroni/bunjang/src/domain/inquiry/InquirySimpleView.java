package shop.makaroni.bunjang.src.domain.inquiry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquirySimpleView {
	private String name;
	private String image;
	private String post;
	private String date;

	public InquirySimpleView(String name, String image, String post, String date) {
		this.name = name;
		this.image = image;
		this.post = post;
		this.date = date;
	}

	public static InquirySimpleView of(InquirySimpleResponse response) {
		return new InquirySimpleView(response.getName(), response.getImage(), response.getPost(), response.getDate());
	}
}
