package shop.makaroni.bunjang.src.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.secret.Secret;
import shop.makaroni.bunjang.src.response.exception.CannotParsingObjectEx;
import shop.makaroni.bunjang.src.response.exception.DoLoginFirstException;

import javax.servlet.http.HttpServletRequest;

import static shop.makaroni.bunjang.src.response.ErrorCode.DO_LOGIN_FIRST_EXCEPTION;

@Service
public class JwtSpecificService {

	public long getUserIdx() throws BaseException {
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

	public String getJwt(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("X-ACCESS-TOKEN");
	}
}
