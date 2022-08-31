package shop.makaroni.bunjang.utils.Itemvalidation;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.item.model.*;
import shop.makaroni.bunjang.src.domain.setting.model.*;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;
import static shop.makaroni.bunjang.utils.Itemvalidation.validationRegex.isRegexPhoneNumber;

public class validation {
    public static void validateItems(ItemReq itemReq) throws BaseException {
        if( (itemReq.getDelivery() != null && !((itemReq.getDelivery() == 0) || (itemReq.getDelivery() == 1))) ||
                (itemReq.getIsNew() != null && !((itemReq.getIsNew() == 0) || (itemReq.getIsNew() == 1))) ||
                (itemReq.getExchange() !=null && !((itemReq.getExchange() == 0) || (itemReq.getExchange() == 1))) ||
                (itemReq.getSafePay() != null && !((itemReq.getSafePay() == 0) || (itemReq.getSafePay() == 1))) ||
                (itemReq.getIsAd() != null && !((itemReq.getIsAd() == 0 ) || (itemReq.getIsAd() == 1)))||
                (itemReq.getInspection()!= null && !((itemReq.getInspection() == 0 ) || (itemReq.getInspection() == 1)))){
            throw new BaseException(REQUEST_ERROR);
        }
        if(itemReq.getName() != null && !validationRegex.isRegexItemName(itemReq.getName())){
            throw new BaseException(POST_ITEM_INVALID_NAME);
        }
        if(itemReq.getCategory() != null && !validationRegex.isRegexCategory(itemReq.getCategory())){
            throw new BaseException(POST_ITEM_INVALID_CATEGORY);
        }
        if(itemReq.getPrice() != null &&(itemReq.getPrice() < 100 || itemReq.getPrice() > 100000000)){
            throw new BaseException(POST_ITEM_INVALID_PRICE);
        }
        if(itemReq.getStock() != null && itemReq.getStock() < 1){
            throw new BaseException(POST_ITEM_INVALID_STOCK);
        }
        if(itemReq.getContent() != null && !validationRegex.isRegexContent(itemReq.getContent())){
            throw new BaseException(POST_ITEM_INVALID_CONTENT);
        }
        if(itemReq.getPrice() != null &&  itemReq.getPrice() < 500){
            throw new BaseException(POST_ITEM_INVALID_SAFEPAY);
        }
    }

    public static void validateAddress(Address address) throws BaseException {
        if(address.getName() != null && (address.getName().length() < 1 || address.getName().length() > 20)){
            throw new BaseException(SETTING_INVALID_ADDR_NAME);
        }
        if(address.getPhoneNum() != null && (!isRegexPhoneNumber(address.getPhoneNum()))){
            throw new BaseException(SETTING_INVALID_PHONE_NUM);
        }
        if(address.getAddress() != null && address.getDetail() == null){
            throw new BaseException(SETTING_INVALID_ADDRESS);
        }
    }
    
    
}
