package shop.makaroni.bunjang.src.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.setting.model.*;
import shop.makaroni.bunjang.src.provider.SettingProvider;
import shop.makaroni.bunjang.src.service.SettingService;
import shop.makaroni.bunjang.utils.JwtService;

import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

@Transactional
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
    public BaseResponse<Notification> patchNotification(Notification notification) {
        if((notification.getNA0100() != null &&
                (Integer.parseInt(notification.getNA0100()) < 0 ||
                Integer.parseInt(notification.getNA0100()) > 23)) ||
            (notification.getNA0101() != null &&
                    (Integer.parseInt(notification.getNA0101()) < 0||
                    Integer.parseInt(notification.getNA0101()) > 23))){
            return new BaseResponse<>(SETTING_INVALID_TIME);
        }
        if(!notification.getNA01() &&
                (notification.getNA0101() != null || notification.getNA0100() != null)){
            return new BaseResponse<>(SETTING_INVALID_SILENCE);
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
            return new BaseResponse<>(settingProvider.getAddress(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
