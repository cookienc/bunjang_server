package shop.makaroni.bunjang.src.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;

import java.util.HashMap;
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
	public ItemRes createItem(ItemReq itemReq) throws BaseException {
		// category validation
		itemProvider.validateCategory(itemReq.getCategory());

		// brand validation
		int itemIdx = itemDao.createItem(itemReq);
		List<String> tags = itemDao.getItemTags(itemIdx);
		setBrand(itemIdx, tags);

		//sellerIdx validation
		if(userDao.checkUserIdx(itemReq.getSellerIdx()) == 0){
			throw new BaseException(POST_ITEM_INVALID_SELLER);
		}

		// add images & tags
		for(String image : itemReq.getImages()){
			itemDao.setImage(itemIdx,image);
		}
		for(String tag : itemReq.getTags()){
			itemDao.setTags(itemIdx,tag);
		}
		return new ItemRes(itemIdx);
	}

	public void setBrand(int itemIdx, List<String> tags) throws BaseException {
		int brandIdx = 0;
		int find;
		for(String tag : tags){
			find = itemDao.findBrand(tag);
			if(find != 0){
				brandIdx = find;
			}
		}
		itemDao.setBrand(itemIdx, brandIdx);
	}

	public void patchItem(int itemIdx, ItemReq itemReq) throws BaseException {
		if (itemReq.getImages().isEmpty()) {
			throw new BaseException(POST_ITEM_EMPTY_IMAGE);
		}
		if (itemReq.getImages() != null) {
			patchImages(itemIdx, itemReq.getImages());
		}
		if (itemReq.getName() != null) {
			patchName(itemIdx, itemReq.getName());
		}
		if (itemReq.getCategory() != null) {
			itemProvider.validateCategory(itemReq.getCategory());
			patchCategory(itemIdx, itemReq.getCategory());
		}
		if (itemReq.getTags() != null) {
			patchTag(itemIdx, itemReq.getTags());
		}
		if(itemReq.getPrice() != null){
			patchPrice(itemIdx, itemReq.getPrice());
		}
		if(itemReq.getDelivery() != null){
			patchDelivery(itemIdx, itemReq.getDelivery());
		}
		if(itemReq.getStock() != null){
			patchStock(itemIdx, itemReq.getStock());
		}
		if(itemReq.getIsNew() != null){
			patchIsNew(itemIdx, itemReq.getIsNew());
		}
		if(itemReq.getExchange() != null){
			patchExchange(itemIdx, itemReq.getExchange());
		}
		if(itemReq.getContent() != null){
			patchContent(itemIdx, itemReq.getContent());
		}
		if(itemReq.getSafePay() != null){
			patchSafePay(itemIdx, itemReq.getSafePay());
		}
		if(itemReq.getSellerIdx() != null){
			patchSellerIdx(itemIdx, itemReq.getSellerIdx());
		}
		if(itemReq.getLocation() != null){
			patchLocation(itemIdx, itemReq.getLocation());
		}
		if(itemReq.getIsAd() != null){
			patchIsAd(itemIdx, itemReq.getIsAd());
		}
		if(itemReq.getInspection() != null){
			patchInspection(itemIdx, itemReq.getInspection());
		}
	}

	private void patchContent(int itemIdx, String content) throws BaseException{
		if(content == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateContent(itemIdx, content);
	}

	private void patchSafePay(int itemIdx, Integer safePay) throws BaseException{
		if(safePay == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateSafePay(itemIdx, safePay);
	}
	private void patchSellerIdx(int itemIdx, Integer sellerIdx) throws BaseException{
		if(sellerIdx == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateSellerIdx(itemIdx, sellerIdx);
	}
	private void patchLocation(int itemIdx, String location) throws BaseException{
		if(location == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateLocation(itemIdx, location);
	}
	private void patchIsAd(int itemIdx, Integer isAd) throws BaseException{
		if(isAd == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateIsAd(itemIdx, isAd);
	}
	private void patchInspection(int itemIdx, Integer inspection) throws BaseException{
		if(inspection == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateInspection(itemIdx, inspection);
	}
	private void patchExchange(int itemIdx, Integer exchange) throws BaseException{
		if(exchange == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateExchange(itemIdx, exchange);
	}

	private void patchIsNew(int itemIdx, Integer isNew) throws BaseException{
		if(isNew == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateIsNew(itemIdx, isNew);
	}
	private void patchStock(int itemIdx, Integer stock) throws BaseException{
		if(stock == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateStock(itemIdx, stock);
	}
	private void patchDelivery(int itemIdx, Integer delivery) throws BaseException{
		if(delivery == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateDelivery(itemIdx, delivery);
	}
	private void patchPrice(int itemIdx, Integer price) throws BaseException{
		if(price == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updatePrice(itemIdx, price);
	}

	public void patchTag(int itemIdx, List<String> tags) throws BaseException {
		if(tags == null){
			throw new BaseException(REQUEST_ERROR);
		}
		int tagIdx;
		deleteAllTags(itemIdx);
		for(String tag : tags){
			if((tagIdx=itemDao.checkTag(itemIdx, tag))==0){
				itemDao.setTag(itemIdx, tag);
			}
			else{
				itemDao.updateTag(tagIdx);
			}
		}
		setBrand(itemIdx, tags);
	}
	public void patchCategory(int itemIdx, String category) throws BaseException {
		if(category == null){
			throw new BaseException(ITEM_INVALID_CATEGORY);
		}
		itemDao.updateCategory(itemIdx, category);
	}
	public void patchName(int itemIdx, String name) throws BaseException {
		if(name == null){
			throw new BaseException(ITEM_NO_NAME);
		}
		if(itemDao.checkItemIdx(itemIdx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}
		itemDao.updateName(itemIdx, name);
	}
	public void patchImages(int itemIdx, List<String> images) throws BaseException{
		// images
		if(images == null){
			throw new BaseException(POST_ITEM_EMPTY_IMAGE);
		}
		int imgIdx;
		deleteAllImages(itemIdx);
		for(String image : images){
			if((imgIdx=itemDao.checkImage(itemIdx, image))==0){
				itemDao.setImage(itemIdx, image);
			}
			else{
				itemDao.updateImage(imgIdx);
			}
		}
	}

	public void deleteAllTags(int itemIdx){
		itemDao.deleteAllTags(itemIdx);
	}

	public void deleteAllImages(int itemIdx){
		itemDao.deleteAllImages(itemIdx);
	}

	public HashMap<String, String> patchStatus(Integer idx, String status) throws BaseException{
		if(itemDao.checkItemIdx(idx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}

		itemDao.patchStatus(idx, status);

		HashMap<String, String> itemRes = new HashMap<>();
		itemRes.put("idx", String.valueOf(idx));
		itemRes.put("status", String.valueOf(itemDao.getStatus(idx)));
		return itemRes;
	}
}
