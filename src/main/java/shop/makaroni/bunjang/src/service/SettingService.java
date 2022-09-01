package shop.makaroni.bunjang.src.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.SettingDao;
import shop.makaroni.bunjang.src.domain.setting.model.Address;
import shop.makaroni.bunjang.src.domain.setting.model.Keyword;
import shop.makaroni.bunjang.src.domain.setting.model.Notification;
import shop.makaroni.bunjang.src.provider.ItemProvider;

import java.util.Objects;

import static shop.makaroni.bunjang.config.BaseResponseStatus.INVALID_USER_JWT;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_ADDRESS_CAPACITY;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_EMPTY_SILENCE;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_INVALID_ADDR_IDX;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_INVALID_KEYWORD_IDX;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_INVALID_SILENCE;
import static shop.makaroni.bunjang.config.BaseResponseStatus.SETTING_KEYWORD_CAPACITY;


@Service
@Transactional
public class SettingService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SettingDao settingDao;
    private final ItemDao itemDao;
    private final ItemProvider itemProvider;

    public SettingService(SettingDao settingDao,  ItemProvider itemProvider, ItemDao itemDao) {
        this.settingDao = settingDao;
        this.itemProvider = itemProvider;
        this.itemDao = itemDao;
    }

    public void patchNotification(Long userIdx, Notification req) throws BaseException {
        Notification res = settingDao.getNotification(userIdx);
        if(req.getNA00() != null){res.setNA00(req.getNA00());}
        if(req.getNA01() != null){
            if(req.getNA01() == false &&
                (req.getNA0100() != null || req.getNA0101() != null)){
                throw new BaseException(SETTING_INVALID_SILENCE);
            }
            res.setNA01(req.getNA01());
            if(req.getNA01() == true &&
                    (req.getNA0100() == null || req.getNA0101() == null)){
                throw new BaseException(SETTING_EMPTY_SILENCE);
            }
        }
        if(req.getNA0100() != null){res.setNA0100(req.getNA0100());}
        if(req.getNA0101() != null){res.setNA0101(req.getNA0101());}
        if(req.getNB00() != null){res.setNB00(req.getNB00());}
        if(req.getNC00() != null){res.setNC00(req.getNC00());}
        if(req.getNC01() != null){res.setNC01(req.getNC01());}
        if(req.getNC02() != null){res.setNC02(req.getNC02());}
        if(req.getNC03() != null){res.setNC03(req.getNC03());}
        if(req.getNC04() != null){res.setNC04(req.getNC04());}
        if(req.getNC05() != null){res.setNC05(req.getNC05());}
        if(req.getND00() != null){res.setND00(req.getND00());}
        if(req.getND01() != null){res.setND01(req.getND01());}
        if(req.getND02() != null){res.setND02(req.getND02());}
        if(req.getNE00() != null){res.setNE00(req.getNE00());}
        if(req.getNE01() != null){res.setNE01(req.getNE01());}
        if(req.getNF00() != null){res.setNF00(req.getNF00());}
        if(req.getNG00() != null){res.setNG00(req.getNG00());}
        if(req.getNG01() != null){res.setNG01(req.getNG01());}

        settingDao.patchNotification(userIdx, res);
    }

    public Long postAddress(Long userIdx, Address req) throws BaseException {
        if(settingDao.getUserAddress(userIdx).size() >= 10){
            throw new BaseException(SETTING_ADDRESS_CAPACITY);
        }
        Long addrIdx = settingDao.postAddress(userIdx, req);
        settingDao.deleteAddrDefault(userIdx, addrIdx);
        return addrIdx;
    }

    public void patchAddress(Long userIdx, Long idx, Address req) throws BaseException {
        if(settingDao.checkAddress(idx) == 0){
            throw new BaseException(SETTING_INVALID_ADDR_IDX);
        }
        if(!Objects.equals(settingDao.getAddressUser(idx), userIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }
        Address res = settingDao.getAddress(idx);
        if(req.getIdx() != null){res.setIdx(req.getIdx());}
        if(req.getName() != null){res.setName(req.getName());}
        if(req.getPhoneNum() != null){res.setPhoneNum(req.getPhoneNum());}
        if(req.getAddress() != null){res.setAddress(req.getAddress());}
        if(req.getDetail() != null){res.setDetail(req.getDetail());}
        if(req.getIsDefault() != null){res.setIsDefault(req.getIsDefault());}
        settingDao.patchAddress(idx, res);
    }

    public void deleteAddress(Long userIdx, Long idx) throws BaseException {
        if(settingDao.checkAddress(idx) == 0){
            throw new BaseException(SETTING_INVALID_ADDR_IDX);
        }
        if(!Objects.equals(settingDao.getAddressUser(idx), userIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }

        settingDao.deleteAddress(idx);
    }

    public Long postKeyword(Long userIdx, Keyword req) throws BaseException {
        try{
            if(req.getCategory() == null) {
                req.setCategory("E");
            }
            else{
                itemProvider.validateCategory(req.getCategory());
            }
            if(req.getMinPrice() == null){
                req.setMinPrice("0");
            }
            if(req.getMaxPrice() == null){
                req.setMaxPrice("0");
            }
            if(settingDao.getKeywordCnt(userIdx) >= 50){
                throw new BaseException(SETTING_KEYWORD_CAPACITY);
            }
            return settingDao.postKeyword(userIdx, req);
        }
        catch(BaseException baseException){
            throw new BaseException(baseException.getStatus());
        }
    }

    public void deleteKeyword(Long userIdx, Long idx) throws BaseException {
        if(settingDao.checkKeyword(idx) == 0){
            throw new BaseException(SETTING_INVALID_KEYWORD_IDX);
        }
        if(!Objects.equals(settingDao.getKeywordUser(idx), userIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }

        settingDao.deleteKeyword(idx);
    }

    public void patchKeyword(Long userIdx, Long idx, Keyword req) throws BaseException {
        if(settingDao.checkKeyword(idx) == 0){
            throw new BaseException(SETTING_INVALID_KEYWORD_IDX);
        }
        if(!Objects.equals(settingDao.getKeywordUser(idx), userIdx)){
            throw new BaseException(INVALID_USER_JWT);
        }
        if(settingDao.getKeywordCnt(userIdx) >= 50){
            throw new BaseException(SETTING_KEYWORD_CAPACITY);
        }
        Keyword res = settingDao.getKeyword(idx);
        if(req.getNotification() != null){res.setNotification(req.getNotification());}
        if(req.getLocation() != null){res.setLocation(req.getLocation());}
        if(req.getMinPrice() != null){res.setMinPrice(req.getMinPrice());}
        if(req.getMaxPrice() != null){res.setMaxPrice(req.getMaxPrice());}
        if(req.getCategory() != null){
            itemProvider.validateCategory(req.getCategory());
            res.setCategory(req.getCategory());
        }
        settingDao.patchKeyword(idx, res);
    }
}
