package shop.makaroni.bunjang.src.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import shop.makaroni.bunjang.src.domain.login.LoginRequest;
import shop.makaroni.bunjang.src.domain.sms.MessagesRequest;
import shop.makaroni.bunjang.src.domain.sms.SendSmsResponse;
import shop.makaroni.bunjang.src.domain.sms.SmsRequest;
import shop.makaroni.bunjang.src.domain.user.PhoneNumber;
import shop.makaroni.bunjang.src.domain.user.SmsLoginRequest;
import shop.makaroni.bunjang.src.domain.user.dto.SaveUserRequest;
import shop.makaroni.bunjang.src.response.exception.AuthCodeNotMatchEx;
import shop.makaroni.bunjang.src.response.exception.CannotFindAuthNumberEx;
import shop.makaroni.bunjang.src.response.exception.DoAuthorizeFirstEx;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static shop.makaroni.bunjang.src.response.ErrorCode.CANNOT_FIND_AUTH_NUMBER_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.DO_AUTH_FIRST_EXCEPTION;
import static shop.makaroni.bunjang.src.response.ErrorCode.NOT_MATCH_AUTH_CODE_EXCEPTION;

@Service
@Transactional
@RequiredArgsConstructor
public class NaverService {

	private static final String AUTH_NUMBER = "Auth-Number";

	private final LoginService loginService;

	@Value("${naver.serviceId}")
	private String serviceId;

	@Value("${naver.accessKey}")
	private String accessKey;

	@Value("${naver.secretKey}")
	private String secretKey;

	@Value("${naver.password}")
	private String password;

	public SendSmsResponse sendSms(HttpSession session, PhoneNumber recipientPhoneNumber) throws JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {
		Long time = System.currentTimeMillis();
		List<MessagesRequest> messages = new ArrayList<>();
		messages.add(new MessagesRequest(recipientPhoneNumber.getPhoneNumber(), getContent(session)));

		SmsRequest smsRequest = new SmsRequest("SMS", "COMM", "82", "01039500885", "bunzang", messages);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(smsRequest);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", this.accessKey);
		String sig = makeSignature(time);
		headers.set("x-ncp-apigw-signature-v2", sig);

		HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		URI url = new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages");

		return restTemplate.postForObject(url, body, SendSmsResponse.class);
	}

	private String getContent(HttpSession session) {
		return "[번개장터] 인증번호 [" + randomNumber(session) + "]를 입력해주세요.";
	}

	public String makeSignature(Long time) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + serviceId + "/messages";
		String timestamp = time.toString();

		String message = new StringBuilder()
				.append(method)
				.append(space)
				.append(url)
				.append(newLine)
				.append(timestamp)
				.append(newLine)
				.append(accessKey)
				.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}

	public boolean checkingCode(HttpSession session, String authNumber) {
		boolean isCheck = false;
		isCheck = checkAuthCode(session, authNumber);
		return isCheck;
	}

	private boolean checkAuthCode(HttpSession session, String authNumber) {
		String sessionAuth = String.valueOf(session.getAttribute(AUTH_NUMBER));

		if (sessionAuth == null || sessionAuth.isBlank()) {
			throw new CannotFindAuthNumberEx(CANNOT_FIND_AUTH_NUMBER_EXCEPTION.getMessages());
		}

		if (!authNumber.equals(sessionAuth)) {
			throw new AuthCodeNotMatchEx(NOT_MATCH_AUTH_CODE_EXCEPTION.getMessages());
		}

		return true;
	}

	public String smsLogin(SmsLoginRequest request) {
		if (!request.getIsChecked()) {
			throw new DoAuthorizeFirstEx(DO_AUTH_FIRST_EXCEPTION.getMessages());
		}

		String id = createId(request.getBirthNumber(), request.getPhoneNumber());

		if (loginService.alreadySignUp(id)) {
			return loginService.login(new LoginRequest(id, password));
		}

		return loginService.saveAndLogin(new SaveUserRequest(id, password));
	}

	private String createId(String birthNumber, String phoneNumber) {
		return "sms" + birthNumber.substring(1, 4) + phoneNumber.substring(3, 7);
	}

	private int randomNumber(HttpSession session) {
		int randomNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
		session.setAttribute(AUTH_NUMBER, randomNumber);
		return randomNumber;
	}
}
