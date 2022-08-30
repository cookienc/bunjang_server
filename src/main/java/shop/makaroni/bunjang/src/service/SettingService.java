package shop.makaroni.bunjang.src.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.SettingDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;
import shop.makaroni.bunjang.src.provider.SettingProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;
import static shop.makaroni.bunjang.utils.Itemvalidation.validationRegex.*;


@Service
@Transactional
public class SettingService {
    private final SettingDao settingDao;
    private final SettingProvider settingProvider;

    public SettingService(SettingDao settingDao, SettingProvider settingProvider) {
        this.settingDao = settingDao;
        this.settingProvider = settingProvider;
    }
}
