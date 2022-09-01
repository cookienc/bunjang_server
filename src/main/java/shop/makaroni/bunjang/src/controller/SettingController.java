package shop.makaroni.bunjang.src.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.setting.model.Address;
import shop.makaroni.bunjang.src.domain.setting.model.GetKeywordRes;
import shop.makaroni.bunjang.src.domain.setting.model.Keyword;
import shop.makaroni.bunjang.src.domain.setting.model.Notification;
import shop.makaroni.bunjang.src.provider.SettingProvider;
import shop.makaroni.bunjang.src.service.SettingService;
import shop.makaroni.bunjang.utils.JwtService;

import java.util.HashMap;
import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.REQUEST_ERROR;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_INVALID_ADDR_IDX;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_INVALID_KEYWORD_IDX;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_INVALID_TIME;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_KEYWORD_IMMUTABLE;
import static shop.makaroni.bunjang.utils.Itemvalidation.validation.validateAddress;
import static shop.makaroni.bunjang.utils.Itemvalidation.validation.validateKeyword;
@RestController
@RequestMapping("/settings")
public class SettingController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final SettingProvider settingProvider;
    @Autowired
    private final SettingService settingService;
    @Autowired
    JwtService jwtService;

    public SettingController(SettingProvider settingProvider, SettingService settingService, JwtService jwtService) {
        this.settingProvider = settingProvider;
        this.settingService = settingService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/notifications")
    public BaseResponse<Notification> getNotification() {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(settingProvider.getNotification(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/notifications")
    public BaseResponse<Notification> patchNotification(@RequestBody Notification notification) {
        if((notification.getNA0100() != null &&
                (Integer.parseInt(notification.getNA0100()) < 0 ||
                Integer.parseInt(notification.getNA0100()) > 23)) ||
            (notification.getNA0101() != null &&
                    (Integer.parseInt(notification.getNA0101()) < 0||
                    Integer.parseInt(notification.getNA0101()) > 23))){
            return new BaseResponse<>(SETTING_INVALID_TIME);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            settingService.patchNotification(userIdx, notification);
            return new BaseResponse<>(settingProvider.getNotification(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("/addresses")
    public BaseResponse<List<Address>> getAddress() {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(settingProvider.getUserAddress(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/addresses")
    public BaseResponse<HashMap<String, String>> postAddress(@RequestBody Address req) {
        if(req.getName() == null || req.getPhoneNum() == null || req.getAddress() == null ||
                req.getDetail() == null || req.getIsDefault() == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            validateAddress(req);
            Long addrIdx = settingService.postAddress(userIdx,req);
            HashMap<String, String> res = new HashMap<>();
            res.put("idx", String.valueOf(addrIdx));
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/addresses/{idx}")
    public BaseResponse<HashMap<String, String>> patchAddress(@PathVariable("idx") Long idx,
                                                            @RequestBody Address req) {
        if(idx <= 0){
            return new BaseResponse<>(SETTING_INVALID_ADDR_IDX);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            validateAddress(req);
            settingService.patchAddress(userIdx, idx, req);

            HashMap<String, String> res = new HashMap<>();
            res.put("idx", String.valueOf(idx));
            return new BaseResponse<>(res);
        }
        catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }

    }
    @ResponseBody
    @DeleteMapping("/addresses/{idx}")
    public BaseResponse<HashMap<String, String>> DeleteAddress(@PathVariable("idx") Long idx) {
        if(idx <= 0){
            return new BaseResponse<>(SETTING_INVALID_ADDR_IDX);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            settingService.deleteAddress(userIdx, idx);
            HashMap<String, String> res = new HashMap<>();
            res.put("idx", String.valueOf(idx));
            return new BaseResponse<>(res);
        }
        catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/keywords")
    public BaseResponse<GetKeywordRes> getKeyword(@RequestParam() Integer page) {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(settingProvider.getKeyword(userIdx, page));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @PostMapping("/keywords")
    public BaseResponse<HashMap<String, String>> postKeyword(@RequestBody Keyword req) {
        if(req.getKeyword() == null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            validateKeyword(req);
            Long keywordIdx = settingService.postKeyword(userIdx,req);
            HashMap<String, String> res = new HashMap<>();
            res.put("idx", String.valueOf(keywordIdx));
            return new BaseResponse<>(res);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/keywords/{idx}")
    public BaseResponse<HashMap<String, String>> DeleteKeyword(@PathVariable("idx") Long idx) {
        if(idx <= 0){
            return new BaseResponse<>(SETTING_INVALID_KEYWORD_IDX);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            settingService.deleteKeyword(userIdx, idx);
            HashMap<String, String> res = new HashMap<>();
            res.put("idx", String.valueOf(idx));
            return new BaseResponse<>(res);
        }
        catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/keywords/{idx}")
    public BaseResponse<HashMap<String, String>> patchKeyword(@PathVariable("idx") Long idx,
                                                              @RequestBody Keyword req) {
        if (idx <= 0) {
            return new BaseResponse<>(SETTING_INVALID_KEYWORD_IDX);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            validateKeyword(req);
            if(req.getKeyword() != null){
                return new BaseResponse<>(SETTING_KEYWORD_IMMUTABLE);
            }
            settingService.patchKeyword(userIdx, idx, req);

            HashMap<String, String> res = new HashMap<>();
            res.put("idx", String.valueOf(idx));
            return new BaseResponse<>(res);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
