package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.login.LoginRequest;
import shop.makaroni.bunjang.src.response.ResponseInfoWithJwt;
import shop.makaroni.bunjang.src.service.LoginService;

import javax.validation.Valid;

import static shop.makaroni.bunjang.src.response.SuccessStatus.LOGIN_SUCCESS;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping
	public ResponseEntity<ResponseInfoWithJwt> login(@Valid @RequestBody LoginRequest loginRequest) {
		String jwt = loginService.login(loginRequest);
		return ResponseEntity.ok(ResponseInfoWithJwt.of(LOGIN_SUCCESS, jwt));
	}
}
