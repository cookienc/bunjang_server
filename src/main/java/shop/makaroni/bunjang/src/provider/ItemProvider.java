package shop.makaroni.bunjang.src.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;

import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

@Service
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
			return getItemRes;
		} catch (Exception exception) {
			throw new BaseException(RESPONSE_ERROR);
		}
	}

	public List<GetItemRes> getItems() throws BaseException {
		List<GetItemRes> getItemRes;
		try {
			getItemRes = itemDao.getItems();
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

//    public GetCategoryRes getCategory(String code) {
////		GetCategoryRes getCategoryRes = new GetCategoryRes();
////		if(itemDao.getItem())
//    }
}
