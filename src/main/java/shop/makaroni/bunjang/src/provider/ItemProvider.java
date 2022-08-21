package shop.makaroni.bunjang.src.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;

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

	public int getItemWishCnt(int itemIdx){
		return itemDao.getItemWishCnt(itemIdx);
	}

	public int getItemChatCnt(int itemIdx){
		return itemDao.getItemChatCnt(itemIdx);
	}

	public List<String> getItemTags(int itemIdx){
		return itemDao.getItemTags(itemIdx);
	}
	public List<String> getItemImages(int itemIdx){
		return itemDao.getItemImages(itemIdx);
	}
}
