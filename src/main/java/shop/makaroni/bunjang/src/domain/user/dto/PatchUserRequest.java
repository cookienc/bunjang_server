package shop.makaroni.bunjang.src.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Null;

@Getter
public class PatchUserRequest {
	String image;
	String storeName;
	String storeUrl;
	Integer contactStart;
	Integer contactEnd;
	String description;
	String policy;
	String precaution;

	public PatchUserRequest(String image, String storeName, String storeUrl, Integer contactStart, Integer contactEnd, String description, String policy, String precaution) {
		this.image = image;
		this.storeName = storeName;
		this.storeUrl = storeUrl;
		this.contactStart = contactStart;
		this.contactEnd = contactEnd;
		this.description = description;
		this.policy = policy;
		this.precaution = precaution;
	}
}
