package shop.makaroni.bunjang.src.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.secret.Secret;
import shop.makaroni.bunjang.src.user.model.PatchUserReq;
import shop.makaroni.bunjang.src.user.model.PostUserReq;
import shop.makaroni.bunjang.src.user.model.PostUserRes;
import shop.makaroni.bunjang.utils.AES128;
import shop.makaroni.bunjang.utils.JwtService;

import static shop.makaroni.bunjang.config.BaseResponseStatus.DATABASE_ERROR;
import static shop.makaroni.bunjang.config.BaseResponseStatus.MODIFY_FAIL_USERNAME;
import static shop.makaroni.bunjang.config.BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR;
import static shop.makaroni.bunjang.config.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
