package shop.makaroni.bunjang.src.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;
import shop.makaroni.bunjang.src.domain.item.model.GetSearchRes;

import java.sql.Array;
import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.ITEM_NO_EXIST;
import static shop.makaroni.bunjang.config.BaseResponseStatus.RESPONSE_ERROR;

@Service
public class ItemProvider {
	private final ItemDao itemDao;

	@Autowired
	public ItemProvider(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public GetItemRes getItem(int itemIdx) throws BaseException{
		if(itemDao.checkItemIdx(itemIdx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}
		GetItemRes getItemRes = null;
		try{
			getItemRes = itemDao.getItem(itemIdx);
			return getItemRes;
		}catch(Exception exception){
			throw new BaseException(RESPONSE_ERROR);
		}
	}
	public List<GetItemRes> getItems() throws  BaseException{
		List<GetItemRes> getItemRes;
		try{
			getItemRes = itemDao.getItems();
			return getItemRes;
		}catch(Exception exception){
			throw new BaseException(RESPONSE_ERROR);
		}
	}
	public String getItemWishCnt(int itemIdx){
		return String.valueOf(itemDao.getItemWishCnt(itemIdx));
	}

	public String getItemChatCnt(int itemIdx){
		return String.valueOf(itemDao.getItemChatCnt(itemIdx));
	}

	public List<String> getItemTags(int itemIdx){
		return itemDao.getItemTags(itemIdx);
	}
	public List<String> getItemImages(int itemIdx){
		return itemDao.getItemImages(itemIdx);
	}

	public List<GetSearchRes> getSearch(String name, char sort, int count) throws BaseException {
		try{
			List<GetSearchRes> getSearchRes = itemDao.getSearchRes(name, sort, count);
			return getSearchRes;
		}catch(Exception exception){
			throw new BaseException(RESPONSE_ERROR);
		}
	}
}
