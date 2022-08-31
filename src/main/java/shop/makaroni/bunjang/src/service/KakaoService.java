package shop.makaroni.bunjang.src.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shop.makaroni.bunjang.src.domain.login.LoginRequest;
import shop.makaroni.bunjang.src.domain.user.KakaoProfile;
import shop.makaroni.bunjang.src.domain.user.dto.SaveUserRequest;
import shop.makaroni.bunjang.src.response.exception.CannotParsingObjectEx;

import java.io.IOException;
import java.util.NoSuchElementException;

import static shop.makaroni.bunjang.src.response.ErrorCode.CANNOT_PARSING_OBJECT;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String CLIENT_ID = "8e89ed1c355979f20e667f26fd926191";
	public static final String TOKEN_HOST = "https://kauth.kakao.com/oauth/token";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String INFO_HOST = "https://kapi.kakao.com/v2/user/me";

	@Value("${kakao.password}")
	private String key;

	@Value("${kakao.redirect_uri}")
	private String REDIRECT_URI;

	private final LoginService loginService;
	private final UserService userService;

	public String getToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", AUTHORIZATION_CODE);
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JSONObject> response = restTemplate.postForEntity(TOKEN_HOST, request, JSONObject.class);

		isNormal(response.getStatusCode());

		try {
			JSONObject responseBody = response.getBody();
			return (String) responseBody.get(ACCESS_TOKEN);
		} catch (NullPointerException e) {
			throw new CannotParsingObjectEx(CANNOT_PARSING_OBJECT.getMessages());
		}
	}

	public String getJwt(String accessToken) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity request = new HttpEntity(headers);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = template.exchange(INFO_HOST, HttpMethod.GET, request, String.class);

		isNormal(response.getStatusCode());

		KakaoProfile kakaoProfile = getKakaoProfile(response);

		return login(kakaoProfile.getId());
	}

	private void isNormal(HttpStatus response) {
		if (!response.is2xxSuccessful()) {
			throw new NoSuchElementException();
		}
	}

	private KakaoProfile getKakaoProfile(ResponseEntity<String> response) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		KakaoProfile kakaoProfile = mapper.readValue(response.getBody(), KakaoProfile.class);
		return kakaoProfile;
	}

	private String login(Long kakaoId) {
		if (loginService.alreadySignUp(String.valueOf(kakaoId))) {
			return loginService.login(LoginRequest.of(kakaoId, key));
		}

		return loginService.saveAndLogin(SaveUserRequest.of(kakaoId, key));
	}
}
