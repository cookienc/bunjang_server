package shop.makaroni.bunjang.src.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.item.model.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;
import static shop.makaroni.bunjang.utils.Itemvalidation.validationRegex.*;


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
	public ItemRes createItem(Long sellerIdx, ItemReq itemReq) throws BaseException {
		// category validation
		itemProvider.validateCategory(itemReq.getCategory());

		// brand validation
		Long itemIdx = itemDao.createItem(sellerIdx, itemReq);
		List<String> tags = itemDao.getItemTags(itemIdx);
		setBrand(itemIdx, tags);

		// add images & tags
		for(String image : itemReq.getImages()){
			itemDao.setImage(itemIdx,image);
		}
		for(String tag : itemReq.getTags()){
			itemDao.setTags(itemIdx,tag);
		}
		return new ItemRes(itemIdx);
	}

	public void setBrand(Long itemIdx, List<String> tags) throws BaseException {
		Long brandIdx = Long.valueOf(0);
		Long find;
		for(String tag : tags){
			find = itemDao.findBrand(tag);
			if(find != 0){
				brandIdx = find;
			}
		}
		itemDao.setBrand(itemIdx, brandIdx);
	}

	public void patchItem(Long itemIdx, Long sellerIdx, ItemReq itemReq) throws BaseException {

		if(itemDao.getSellerIdx(itemIdx) != sellerIdx ){
			throw new BaseException(INVALID_USER_JWT);
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

	private void patchContent(Long itemIdx, String content) throws BaseException{
		if(content == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateContent(itemIdx, content);
	}

	private void patchSafePay(Long itemIdx, Integer safePay) throws BaseException{
		if(safePay == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateSafePay(itemIdx, safePay);
	}
	private void patchSellerIdx(Long itemIdx, Long sellerIdx) throws BaseException{
		if(sellerIdx == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateSellerIdx(itemIdx, sellerIdx);
	}
	private void patchLocation(Long itemIdx, String location) throws BaseException{
		if(location == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateLocation(itemIdx, location);
	}
	private void patchIsAd(Long itemIdx, Integer isAd) throws BaseException{
		if(isAd == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateIsAd(itemIdx, isAd);
	}
	private void patchInspection(Long itemIdx, Integer inspection) throws BaseException{
		if(inspection == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateInspection(itemIdx, inspection);
	}
	private void patchExchange(Long itemIdx, Integer exchange) throws BaseException{
		if(exchange == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateExchange(itemIdx, exchange);
	}

	private void patchIsNew(Long itemIdx, Integer isNew) throws BaseException{
		if(isNew == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateIsNew(itemIdx, isNew);
	}
	private void patchStock(Long itemIdx, Integer stock) throws BaseException{
		if(stock == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateStock(itemIdx, stock);
	}
	private void patchDelivery(Long itemIdx, Integer delivery) throws BaseException{
		if(delivery == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updateDelivery(itemIdx, delivery);
	}
	private void patchPrice(Long itemIdx, Integer price) throws BaseException{
		if(price == null){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.updatePrice(itemIdx, price);
	}

	public void patchTag(Long itemIdx, List<String> tags) throws BaseException {
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
	public void patchCategory(Long itemIdx, String category) throws BaseException {
		if(category == null){
			throw new BaseException(ITEM_INVALID_CATEGORY);
		}
		itemDao.updateCategory(itemIdx, category);
	}
	public void patchName(Long itemIdx, String name) throws BaseException {
		if(name == null){
			throw new BaseException(ITEM_NO_NAME);
		}
		if(itemDao.checkItemIdx(itemIdx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}
		itemDao.updateName(itemIdx, name);
	}
	public void patchImages(Long itemIdx, List<String> images) throws BaseException{
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

	public void deleteAllTags(Long itemIdx){
		itemDao.deleteAllTags(itemIdx);
	}

	public void deleteAllImages(Long itemIdx){
		itemDao.deleteAllImages(itemIdx);
	}

	public HashMap<String, String> PatchItemStatus(Long idx, Long userIdx, String status) throws BaseException{
		if(!Objects.equals(itemDao.getSellerIdx(idx), userIdx)){
			throw new BaseException(INVALID_USER_JWT);
		}
		if(itemDao.checkItemIdx(idx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}

		itemDao.patchStatus(idx, status);

		HashMap<String, String> itemRes = new HashMap<>();
		itemRes.put("idx", String.valueOf(idx));
		itemRes.put("status", String.valueOf(itemDao.getStatus(idx)));
		return itemRes;
	}

	public HashMap<String, String> PostWish(Long itemIdx, Long userIdx) throws BaseException {
		if(userDao.checkUserIdx(userIdx) == 0){
			throw new BaseException(USERS_INVALID_IDX);
		}
		if(itemDao.checkItemIdx(itemIdx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}
		itemDao.postWish(itemIdx, userIdx);
		HashMap<String,String> res = new HashMap<>();
		res.put("itemIdx", String.valueOf(itemIdx));
		return res;

	}

	public HashMap<String, String> DeleteWish(Long itemIdx, Long userIdx) throws BaseException {
		if(userDao.checkUserIdx(userIdx) == 0){
			throw new BaseException(USERS_INVALID_IDX);
		}
		if(itemDao.checkItemIdx(itemIdx) == 0){
			throw new BaseException(ITEM_NO_EXIST);
		}
		if(itemDao.checkWishList(userIdx, itemIdx) == 0){
			throw new BaseException(REQUEST_ERROR);
		}
		itemDao.deleteWish(itemIdx, userIdx);
		HashMap<String,String> res = new HashMap<>();
		res.put("itemIdx", String.valueOf(itemIdx));
		return res;
	}

    public void postReport(Long userIdx, PostReportReq postReportReq) throws BaseException {
		if(itemDao.checkItemIdx(postReportReq.getTarget()) == 0){
			throw new BaseException(POST_REPORT_INVALID_TARGET);
		}
		if(!isRegexReportType(postReportReq.getType())){
			throw new BaseException(POST_REPORT_INVALID_TYPE);
		}
		itemDao.postReport(userIdx, postReportReq);
	}
}
