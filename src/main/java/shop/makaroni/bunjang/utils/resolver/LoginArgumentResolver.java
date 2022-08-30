package shop.makaroni.bunjang.utils.resolver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.secret.Secret;
import shop.makaroni.bunjang.src.response.exception.CannotParsingObjectEx;
import shop.makaroni.bunjang.src.response.exception.DoLoginFirstException;

import javax.servlet.http.HttpServletRequest;

import static shop.makaroni.bunjang.src.response.ErrorCode.DO_LOGIN_FIRST_EXCEPTION;

@Slf4j
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(Login.class) != null;
	}

	@Override
	public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Long userIdx = getUserIdx();

		if (userIdx == null) {
			throw new DoLoginFirstException(DO_LOGIN_FIRST_EXCEPTION.getMessages());
		}

		return userIdx;
	}

	private long getUserIdx() throws BaseException {
		String accessToken = getJwt();

		if(accessToken == null || accessToken.length() == 0){
			throw new DoLoginFirstException(DO_LOGIN_FIRST_EXCEPTION.getMessages());
		}

		// 2. JWT parsing
		Jws<Claims> claims;
		try{
			claims = Jwts.parser()
					.setSigningKey(Secret.JWT_SECRET_KEY)
					.parseClaimsJws(accessToken);
		} catch (Exception ignored) {
			throw new CannotParsingObjectEx("유효하지 않은 JWT 입니다.");
		}

		// 3. userIdx 추출
		return claims.getBody().get("userIdx", Long.class);
	}

	private String getJwt(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("X-ACCESS-TOKEN");
	}
}
