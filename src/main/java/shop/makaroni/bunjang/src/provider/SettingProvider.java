package shop.makaroni.bunjang.src.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.SettingDao;
import shop.makaroni.bunjang.src.domain.setting.model.Address;
import shop.makaroni.bunjang.src.domain.setting.model.Keyword;
import shop.makaroni.bunjang.src.domain.setting.model.Notification;

import java.util.List;

@Service
@Transactional

public class SettingProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SettingDao settingDao;
    @Autowired
    public SettingProvider(SettingDao settingDao) {
        this.settingDao = settingDao;
    }

    public Notification getNotification(Long userIdx) {
        return settingDao.getNotification(userIdx);
    }

    public List<Address> getUserAddress(Long userIdx) {
        return settingDao.getUserAddress(userIdx);
    }

    public Address getAddress(Long addrIdx) {
        return settingDao.getAddress(addrIdx);
    }

    public List<Keyword> getKeyword(Long userIdx) {
        return settingDao.getKeyword(userIdx);
    }
}
