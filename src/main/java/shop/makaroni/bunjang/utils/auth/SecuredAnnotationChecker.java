package shop.makaroni.bunjang.utils.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.response.ErrorCode;
import shop.makaroni.bunjang.src.response.exception.DoLoginFirstException;
import shop.makaroni.bunjang.src.response.exception.UnAuthorizedException;
import shop.makaroni.bunjang.src.service.JwtSpecificService;

import static shop.makaroni.bunjang.src.response.ErrorCode.DO_LOGIN_FIRST_EXCEPTION;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecuredAnnotationChecker {

	private final JwtSpecificService jwtSpecificService;

	@Before("@annotation(shop.makaroni.bunjang.utils.auth.Secured)")
	public void checkLogin(JoinPoint joinPoint) throws BaseException {
		if ((Long) jwtSpecificService.getUserIdx() == null) {
			throw new DoLoginFirstException(DO_LOGIN_FIRST_EXCEPTION.getMessages());
		}
	}

	@Around("@annotation(shop.makaroni.bunjang.utils.auth.AuthChecker) && args(userIdx,..)")
	public Object checkUser(ProceedingJoinPoint joinPoint, Long userIdx) throws Throwable {
		log.info("userIdx = {}", userIdx);

		if (jwtSpecificService.getUserIdx() != userIdx) {
			throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_EXCEPTION.getMessages());
		}

		return joinPoint.proceed();
	}
}
