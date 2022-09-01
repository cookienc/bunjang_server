package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.login.LoginRequest;
import shop.makaroni.bunjang.src.domain.user.PhoneNumber;
import shop.makaroni.bunjang.src.domain.user.SmsLoginRequest;
import shop.makaroni.bunjang.src.provider.UserProvider;
import shop.makaroni.bunjang.src.response.ResponseInfo;
import shop.makaroni.bunjang.src.response.ResponseInfoWithCheck;
import shop.makaroni.bunjang.src.response.ResponseInfoWithJwt;
import shop.makaroni.bunjang.src.response.SuccessStatus;
import shop.makaroni.bunjang.src.response.exception.CanNotIssueAuthCodeException;
import shop.makaroni.bunjang.src.service.KakaoService;
import shop.makaroni.bunjang.src.service.LoginService;
import shop.makaroni.bunjang.src.service.NaverService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static shop.makaroni.bunjang.src.response.ErrorCode.CANNOT_ISSUE_AUTH_CODE_EXCEPTION;
import static shop.makaroni.bunjang.src.response.SuccessStatus.CHECK_LOGIN_ID_SUCCESS;
import static shop.makaroni.bunjang.src.response.SuccessStatus.LOGIN_SUCCESS;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final UserProvider userProvider;
	private final LoginService loginService;
	private final KakaoService kakaoService;
	private final NaverService naverService;

	@GetMapping
	public ResponseEntity<ResponseInfo> checkDuplicateLoginId(@RequestParam String loginId) {
		userProvider.checkDuplicateLoginId(loginId);
		return ResponseEntity.ok(ResponseInfo.of(CHECK_LOGIN_ID_SUCCESS));
	}

	@PostMapping
	public ResponseEntity<ResponseInfoWithJwt> login(@Valid @RequestBody LoginRequest loginRequest) {
		String jwt = loginService.login(loginRequest);
		return ResponseEntity.ok(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}

	@GetMapping("/kakao")
	public ResponseEntity<ResponseInfoWithJwt> getJwtWithAuthorizeCode(@RequestParam String code) throws IOException {
		String accessToken = kakaoService.getToken(code);
		String jwt = kakaoService.getJwt(accessToken);
		return ResponseEntity.ok(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}

	@GetMapping("/kakao/auth")
	public ResponseEntity<ResponseInfoWithJwt> getJwt(@RequestParam("token") String accessToken) throws IOException {
		String jwt = kakaoService.getJwt(accessToken);
		return ResponseEntity.ok(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}

	@PostMapping("/sms/auth")
	public ResponseEntity<ResponseInfo> getMessages(HttpSession session, @Valid @RequestBody PhoneNumber phoneNumber) {
		try {
			naverService.sendSms(session, phoneNumber);
		} catch (Exception e) {
			throw new CanNotIssueAuthCodeException(CANNOT_ISSUE_AUTH_CODE_EXCEPTION.getMessages(), e);
		}
		return ResponseEntity.ok().body(ResponseInfo.of(SuccessStatus.ISSUE_AUTH_CODE_SUCCESS));
	}

	@GetMapping("/sms")
	public ResponseEntity<ResponseInfoWithCheck> smsLogin(HttpSession session, @RequestParam String authNumber) {
		boolean isCheck = naverService.checkingCode(session, authNumber);
		return ResponseEntity.ok().body(ResponseInfoWithCheck.of(SuccessStatus.AUTH_CODE_MATCH_SUCCESS, isCheck));
	}

	@PostMapping("/sms")
	public ResponseEntity<ResponseInfoWithJwt> smsLogin(@Valid @RequestBody SmsLoginRequest request) {
		String jwt = naverService.smsLogin(request);
		return ResponseEntity.ok().body(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}
}
