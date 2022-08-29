package shop.makaroni.bunjang.utils.Itemvalidation;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.domain.item.model.*;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

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
        if(itemReq.getSellerIdx() != null && itemReq.getSellerIdx() < 0){
            throw new BaseException(POST_ITEM_INVALID_SELLER);
        }
    }
}
