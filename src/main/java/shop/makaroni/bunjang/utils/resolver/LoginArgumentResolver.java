package shop.makaroni.bunjang.utils.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shop.makaroni.bunjang.src.response.ErrorCode;
import shop.makaroni.bunjang.src.response.exception.DoLoginFirstException;
import shop.makaroni.bunjang.utils.JwtService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtService jwtService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(Login.class) != null;
	}

	@Override
	public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Long userIdx = jwtService.getUserIdx();

		if (userIdx == null) {
			throw new DoLoginFirstException(ErrorCode.DO_LOGIN_FIRST_EXCEPTION.getMessages());
		}

		return userIdx;
	}
}
