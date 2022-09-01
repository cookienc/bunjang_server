package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;
import static shop.makaroni.bunjang.config.BaseResponseStatus.*;


@Service
@Transactional()
public class ItemProvider {
	private final ItemDao itemDao;
	private final UserDao userDao;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public ItemProvider(ItemDao itemDao, UserDao userDao) {
		this.itemDao = itemDao;
		this.userDao = userDao;
	}

	public GetItemRes getItem(Long itemIdx) throws BaseException {
		if (itemDao.checkItemIdx(itemIdx) == 0) {
			throw new BaseException(ITEM_NO_EXIST);
		}
		if (itemDao.checkItemSold(itemIdx) == 0) {
			throw new BaseException(ITEM_NO_EXIST);
		}
		GetItemRes getItemRes = null;
		try {
			getItemRes = itemDao.getItem(itemIdx);
			getItemRes.setWish(getItemWishCnt(itemIdx));
			getItemRes.setChat(getItemChatCnt(itemIdx));
			getItemRes.setTags(getItemTags(itemIdx));
			getItemRes.setImages(getItemImages(itemIdx));
			return getItemRes;
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public List<GetItemRes> getItems() throws BaseException {
		List<GetItemRes> getItemRes;
		try {
			getItemRes = itemDao.getItems();
			logger.info(String.valueOf(getItemRes.size()));
			for (GetItemRes eachRes : getItemRes) {
				eachRes.setWish(getItemWishCnt(Long.parseLong(eachRes.getIdx())));
				eachRes.setWish(getItemWishCnt(Long.parseLong(eachRes.getIdx())));
				eachRes.setChat(getItemChatCnt(Long.parseLong(eachRes.getIdx())));
				eachRes.setTags(getItemTags(Long.parseLong(eachRes.getIdx())));
				eachRes.setImages(getItemImages(Long.parseLong(eachRes.getIdx())));
			}
			return getItemRes;
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public String getItemWishCnt(Long itemIdx) {
		return String.valueOf(itemDao.getItemWishCnt(itemIdx));
	}

	public String getItemChatCnt(Long itemIdx) {
		return String.valueOf(itemDao.getItemChatCnt(itemIdx));
	}

	public List<String> getItemTags(Long itemIdx) {
		return itemDao.getItemTags(itemIdx);
	}

	public List<String> getItemImages(Long itemIdx) {
		return itemDao.getItemImages(itemIdx);
	}

	public List<GetSearchRes> getSearch(String name, char sort, int page) throws BaseException {
		try {
			return itemDao.getSearchRes(name, sort, page);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public List<GetLogRes> getItemLastN(Long userIdx, int page) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getItemLastN(userIdx, page);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public int getItemCnt(Long brandIdx) throws BaseException {
		if (itemDao.checkBrandIdx(brandIdx) == 0) {
			throw new BaseException(ITEM_INVALID_BRAND);
		}
		try {
			return itemDao.getItemCnt(brandIdx);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public List<GetBrandRes> getBrand(Long userIdx, int page, char sort) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getBrand(userIdx,page, sort);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}
	public List<GetBrandRes> getBrandSearch(Long userIdx, String name, int page) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getBrandSearch(userIdx,name, page);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}


	public List<GetUserBrandRes> getUserBrand(Long userIdx, char sort, int page) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getUserBrand(userIdx, page, sort);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

    public GetCategoryRes getCategory(String code, char sort, int page) throws BaseException{
		try {
			validateCategory(code);
		} catch(BaseException baseException){
			throw new BaseException(baseException.getStatus());
		}
		GetCategoryRes getCategoryRes = new GetCategoryRes();
		HashMap<String,String> codes = validateCategory(code);

		getCategoryRes.setName(itemDao.getCategoryName(codes));
		getCategoryRes.setImage(itemDao.getCategoryImg(codes));
		getCategoryRes.setSubCategory(itemDao.getSubcategory(code));
		getCategoryRes.setItems(itemDao.getCategoryItems(code, sort, page));
		return getCategoryRes;
	}

	public HashMap<String,String> validateCategory(String category) throws BaseException {
		HashMap<String,String> codes = new HashMap<String, String>();
		String parentCode = "E";
		String subCode = category.substring(category.length()-1);
		if(category.length()>2){
			parentCode = category.substring(0,category.length()-2);
			subCode = category.substring(category.length()-2);
		}
		codes.put("parentCode", parentCode);
		codes.put("subCode",subCode);
		if(!category.equals("E") && itemDao.checkCategory(parentCode,subCode) == 0){
			throw new BaseException(POST_ITEM_INVALID_CATEGORY);
		}
		return codes;
	}

	public BaseResponse<GetWisherRes> getWisher(Long idx, int page){
		if(itemDao.checkItemIdx(idx) == 0){
			return new BaseResponse<>(ITEM_NO_EXIST);
		}
		ItemPreview item = new ItemPreview(itemDao.getPrice(idx),
				itemDao.getItemName(idx),
				itemDao.getUpdatedAt(idx));
		int wishCnt = itemDao.getItemWishCnt(idx);
		List<WishList> wishList = itemDao.getWisher(idx,page);
		return new BaseResponse<>(new GetWisherRes(item, wishCnt, wishList));
	}


	public  List<GetWishListRes> getWishList(Long userIdx, int page) throws BaseException {
		if(userDao.checkUserIdx(userIdx) == 0){
			throw new BaseException(USERS_INVALID_IDX);
		}
		List<GetWishListRes> res = itemDao.getWishList(userIdx, page);
		for(GetWishListRes each : res){
			each.setImage(max(itemDao.getItemImages(Long.parseLong(each.getItemIdx()))));
		}
		return res;
	}

	public GetSearchWordRes getSearchWord(String q, int page) throws BaseException {
		List<GetSearchCategoryRes> categories = itemDao.getCategories(q, page);
		for(GetSearchCategoryRes category : categories){
			HashMap<String,String> codes = validateCategory(category.getParent());
			category.setParent(itemDao.getCategoryName(codes));
		}
		return new GetSearchWordRes(categories,itemDao.getWords(q, page));

	}

	public List<GetDealRes> getDeals(Long userIdx, String tab, String status, int page) {
		List<GetDealRes> res;
		if(tab.equals("order")){
			res = itemDao.getDeals(userIdx, "buyerIdx", status, page);
		}
		else{
			res = itemDao.getDeals(userIdx, "sellerIdx", status, page);
		}

		for(GetDealRes each : res){
			Long itemIdx = Long.parseLong(each.getItemIdx());
			each.setImage(max(getItemImages(itemIdx)));
			each.setChat(getItemChatCnt(itemIdx));
			each.setReviewIdx(String.valueOf(itemDao.getReviewIdx(itemIdx)));
		}
		return res;
	}

	public List<GetRecommendRes> getRecommend(Long userIdx, int page) throws BaseException {
		if(userDao.checkUserIdx(userIdx) == 0){
			throw new BaseException(INVALID_USER_JWT);
		}
		List<GetRecommendRes> res;
		if(itemDao.checkLog(userIdx) == 0){
			res = itemDao.getRecommend(0L, page);
		}
		else{
			res = itemDao.getRecommend(userIdx, page);
		}
		for (GetRecommendRes each : res) {
			each.setWishCnt(String.valueOf(itemDao.getItemWishCnt(Long.parseLong(each.getIdx()))));
			each.setIsWished(itemDao.checkWishList(userIdx, Long.parseLong(each.getIdx())) == 1);
			each.setImage(String.valueOf(itemDao.getItemImages(Long.parseLong(each.getIdx()))));
		}

		return res;
	}

//	public List<GetDealRes> getSale(Long userIdx, String status) {
//		List<GetDealRes> res = itemDao.getSale(userIdx, status);
//		for(GetDealRes each : res){
//			Long itemIdx = Long.parseLong(each.getItemIdx());
//			each.setImage(max(getItemImages(itemIdx)));
//			each.setChat(getItemChatCnt(itemIdx));
//			each.setReviewIdx(String.valueOf(itemDao.getReviewIdx(itemIdx)));
//		}
//		return res;
//	}
}
