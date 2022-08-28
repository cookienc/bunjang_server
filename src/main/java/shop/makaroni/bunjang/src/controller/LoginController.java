package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.login.LoginRequest;
import shop.makaroni.bunjang.src.response.ResponseInfoWithJwt;
import shop.makaroni.bunjang.src.service.KakaoService;
import shop.makaroni.bunjang.src.service.LoginService;

import javax.validation.Valid;
import java.io.IOException;

import static shop.makaroni.bunjang.src.response.SuccessStatus.LOGIN_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final KakaoService kakaoService;

	@PostMapping
	public ResponseEntity<ResponseInfoWithJwt> login(@Valid @RequestBody LoginRequest loginRequest) {
		String jwt = loginService.login(loginRequest);
		return ResponseEntity.ok(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}

	@GetMapping("/kakao")
	public ResponseEntity<ResponseInfoWithJwt> getJwt(@RequestParam String code) throws IOException {
		String accessToken = kakaoService.getToken(code);
		String jwt = kakaoService.getJwt(accessToken);
		return ResponseEntity.ok(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}
}
