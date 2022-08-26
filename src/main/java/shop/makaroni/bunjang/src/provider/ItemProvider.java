package shop.makaroni.bunjang.src.provider;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;

import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

@Service
@Transactional
public class ItemProvider {
	private final ItemDao itemDao;
	private final UserDao userDao;

	@Autowired
	public ItemProvider(ItemDao itemDao, UserDao userDao) {
		this.itemDao = itemDao;
		this.userDao = userDao;
	}

	public GetItemRes getItem(int itemIdx) throws BaseException {
		if (itemDao.checkItemIdx(itemIdx) == 0) {
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
			for (GetItemRes eachRes : getItemRes) {
				eachRes.setWish(getItemWishCnt(Integer.parseInt(eachRes.getIdx())));
				eachRes.setWish(getItemWishCnt(Integer.parseInt(eachRes.getIdx())));
				eachRes.setChat(getItemChatCnt(Integer.parseInt(eachRes.getIdx())));
				eachRes.setTags(getItemTags(Integer.parseInt(eachRes.getIdx())));
				eachRes.setImages(getItemImages(Integer.parseInt(eachRes.getIdx())));
			}
			return getItemRes;
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public String getItemWishCnt(int itemIdx) {
		return String.valueOf(itemDao.getItemWishCnt(itemIdx));
	}

	public String getItemChatCnt(int itemIdx) {
		return String.valueOf(itemDao.getItemChatCnt(itemIdx));
	}

	public List<String> getItemTags(int itemIdx) {
		return itemDao.getItemTags(itemIdx);
	}

	public List<String> getItemImages(int itemIdx) {
		return itemDao.getItemImages(itemIdx);
	}

	public List<GetSearchRes> getSearch(String name, char sort, int count) throws BaseException {
		try {
			return itemDao.getSearchRes(name, sort, count);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public List<GetLogRes> getItemLastN(int userIdx, int count) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getItemLastN(userIdx, count);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public int getItemCnt(int brandIdx) throws BaseException {
		if (itemDao.checkBrandIdx(brandIdx) == 0) {
			throw new BaseException(ITEM_INVALID_BRAND);
		}
		try {
			return itemDao.getItemCnt(brandIdx);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public List<GetBrandRes> getBrand(int userIdx, char sort) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getBrand(userIdx, sort);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}
	public List<GetBrandRes> getBrandSearch(int userIdx, String name) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getBrandSearch(userIdx,name);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}


	public List<GetUserBrandRes> getUserBrand(int userIdx, char sort) throws BaseException {
		if (userDao.checkUserIdx(userIdx) == 0) {
			throw new BaseException(USERS_INVALID_IDX);
		}
		try {
			return itemDao.getUserBrand(userIdx, sort);
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

    public GetCategoryRes getCategory(String code, char sort, int count) throws BaseException{
		try {
			validateCategory(code);
		} catch(BaseException baseException){
			throw new BaseException(baseException.getStatus());
		}
		GetCategoryRes getCategoryRes = new GetCategoryRes();
		getCategoryRes.setSubCategory(itemDao.getSubcategory(code));
		getCategoryRes.setItems(itemDao.getCategoryItems(code, sort, count));

		return getCategoryRes;
	}

	public void validateCategory(String category) throws BaseException {
		String parentCode = "E";
		String subCode = category.substring(category.length()-1);
		if(category.length()>2){
			parentCode = category.substring(0,category.length()-2);
			subCode = category.substring(category.length()-2);
		}
		if(!category.equals("E") && itemDao.checkCategory(parentCode,subCode) == 0){
			throw new BaseException(POST_ITEM_INVALID_CATEGORY);
		}
	}

}
