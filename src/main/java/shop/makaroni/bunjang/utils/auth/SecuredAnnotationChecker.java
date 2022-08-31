package shop.makaroni.bunjang.utils.auth;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.response.exception.DoLoginFirstException;
import shop.makaroni.bunjang.src.service.JwtSpecificService;

import static shop.makaroni.bunjang.src.response.ErrorCode.DO_LOGIN_FIRST_EXCEPTION;

@Aspect
@Component
@RequiredArgsConstructor
public class SecuredAnnotationChecker {

	private final JwtSpecificService jwtSpecificService;

	@Before("@annotation(shop.makaroni.bunjang.utils.auth.Secured)")
	public void checkUser(JoinPoint joinPoint) throws BaseException {
		if ((Long) jwtSpecificService.getUserIdx() == null) {
			throw new DoLoginFirstException(DO_LOGIN_FIRST_EXCEPTION.getMessages());
		}
	}
}
