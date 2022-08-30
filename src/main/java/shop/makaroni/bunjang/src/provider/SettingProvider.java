package shop.makaroni.bunjang.src.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.SettingDao;
import shop.makaroni.bunjang.src.domain.setting.model.GetNotificationRes;

@Service
@Transactional

public class SettingProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SettingDao settingDao;
    @Autowired
    public SettingProvider(SettingDao settingDao) {
        this.settingDao = settingDao;
    }

    public GetNotificationRes getNotification(Long userIdx) {
        return settingDao.getNotification(userIdx);
    }
}
