package shop.makaroni.bunjang.src.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {
	private Long id;
	private String connected_at;
	private KakaoAccount kakao_account;
	private Properties properties;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KakaoAccount {
		private Boolean profile_nickname_needs_agreement;
		private Profile profile;
		private Boolean has_email;
		private Boolean email_needs_agreement;
		private Boolean is_email_valid;
		private Boolean is_email_verified;
		private String email;

		@Getter
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Profile {
			private String nickname;
		}
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Properties {
		private String nickname;
	}
}
