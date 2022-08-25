package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;

import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;


@Service
@Transactional
public class ItemService {
	private final ItemDao itemDao;
	private final ItemProvider itemProvider;

	private final UserDao userDao;

	public ItemService(ItemDao itemDao, ItemProvider itemProvider, UserDao userDao) {
		this.itemDao = itemDao;
		this.itemProvider = itemProvider;
		this.userDao = userDao;
	}
	public PostItemRes createItem(PostItemReq postItemReq) throws BaseException {
		// email validation
		String category = postItemReq.getCategory();
		String parentCode = "E";
		String subCode = category.substring(category.length()-1);
		if(category.length()>2){
			parentCode = category.substring(0,category.length()-2);
			subCode = category.substring(category.length()-2);
		}
		if(!category.equals("E") && itemDao.checkCategory(parentCode,subCode) == 0){
			throw new BaseException(POST_ITEM_INVALID_CATEGORY);
		}

		// brand validation
		int brandIdx = 0;
		int itemIdx = itemDao.createItem(postItemReq);
		List<String> tags = itemDao.getItemTags(itemIdx);
		for(String tag : tags){
			brandIdx = itemDao.findBrand(tag);
		}
		itemDao.setBrand(itemIdx, brandIdx);

		//sellerIdx validation
		if(userDao.checkUserIdx(postItemReq.getSellerIdx()) == 0){
			throw new BaseException(POST_ITEM_INVALID_SELLER);
		}


		return new PostItemRes(itemIdx);
	}
}
