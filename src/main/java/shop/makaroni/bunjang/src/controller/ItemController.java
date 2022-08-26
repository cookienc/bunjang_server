package shop.makaroni.bunjang.src.controller;

import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;
import shop.makaroni.bunjang.src.service.ItemService;
import shop.makaroni.bunjang.src.domain.item.model.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;
import static shop.makaroni.bunjang.src.validation.validation.*;


@RestController
@RequestMapping("/items")
@Transactional
public class ItemController {

    @Autowired
    private final ItemProvider itemProvider;
    @Autowired
    private final ItemService itemService;

    public ItemController(ItemProvider itemProvider, ItemService itemService) {
        this.itemProvider = itemProvider;
        this.itemService = itemService;
    }


    @ResponseBody
    @GetMapping("/{itemIdx}")
    public BaseResponse<GetItemRes> getItem(@PathVariable("itemIdx") int itemIdx) {
        try {
            return new BaseResponse<>(itemProvider.getItem(itemIdx));
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/all")
    public BaseResponse<List<GetItemRes>> getItems() {
        try {
            return new BaseResponse<>(itemProvider.getItems());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetSearchRes>> getSearch(@RequestParam() String name,
                                                      @RequestParam(required = false, defaultValue="C") char sort,
                                                      @RequestParam() int count) {
        try {
            if (name == null) {
                throw new BaseException(ITEM_NO_NAME);
            }
            if(count == 0){
                throw new BaseException(ITEM_NO_COUNT);
            }
            if(sort != 'C' && sort != 'R' && sort != 'L' && sort != 'H'){
                 throw new BaseException(ITEM_INVALID_SORT);
            }
            return new BaseResponse<>(itemProvider.getSearch(name, sort, count));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}/last")
    public BaseResponse<List<GetLogRes>> getItemLastN(@PathVariable("userIdx") int userIdx,
                                                      @RequestParam() int count){

        if(userIdx < 0){
            return new BaseResponse<>(USERS_INVALID_IDX);
        }
        if(count <= 0){
            return new BaseResponse<>(ITEM_NO_COUNT);
        }
        try {
            return new BaseResponse<>(itemProvider.getItemLastN(userIdx, count));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("/brand/follow/{userIdx}")
    public BaseResponse<List<GetUserBrandRes>> getUserBrand(@PathVariable("userIdx") int userIdx,
                                                    @RequestParam(required = false, defaultValue = "K") char sort) {


        if(!(sort == 'K' || sort == 'E')){
            return new BaseResponse<>(ITEM_INVALID_SORT);
        }
        List<GetUserBrandRes> getUserBrandRes;
        int brandIdx;
        try {
            if (userIdx <= 0) {
                return new BaseResponse<>(USERS_INVALID_IDX);
            }
            getUserBrandRes = itemProvider.getUserBrand(userIdx,sort);
            for (GetUserBrandRes eachRes : getUserBrandRes) {
                brandIdx = Integer.parseInt(eachRes.getBrandIdx());
                eachRes.setItemCnt(String.valueOf(itemProvider.getItemCnt(brandIdx)));
            }
            return new BaseResponse<>(getUserBrandRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /*
    *
    * 모든 브랜드 목록 및 유저 팔로우 여부 조회
    */
    @ResponseBody
    @GetMapping("/brand/{userIdx}")
    public BaseResponse<List<GetBrandRes>> getBrand(@PathVariable("userIdx") int userIdx,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false, defaultValue = "K") char sort) {

        if(!(sort == 'K' || sort == 'E')){
            return new BaseResponse<>(ITEM_INVALID_SORT);
        }

        List<GetBrandRes> getBrandRes;
        int brandIdx;
        try {
            if (userIdx <= 0) {
                return new BaseResponse<>(USERS_INVALID_IDX);
            }
            if (name == null) {
                getBrandRes = itemProvider.getBrand(userIdx,sort);
            } else {
                getBrandRes = itemProvider.getBrandSearch(userIdx, name);
            }
            for (GetBrandRes eachRes : getBrandRes) {
                brandIdx = Integer.parseInt(eachRes.getBrandIdx());
                eachRes.setItemCnt(String.valueOf(itemProvider.getItemCnt(brandIdx)));
            }
            return new BaseResponse<>(getBrandRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/category/{code}")
    public BaseResponse<GetCategoryRes> getCategory(@PathVariable("code") String code,
                                                    @RequestParam(required = false, defaultValue = "R") char sort,
                                                    @RequestParam() Integer count) {

        if (code.length() > 10 || code.length() < 1 ) {
            return new BaseResponse<>(ITEM_INVALID_CATEGORY);
        }
        if(code.length()!=1 && code.length() % 2 != 0){
            return new BaseResponse<>(ITEM_INVALID_CATEGORY);
        }
        if(count != null && count < 0 ){
            return new BaseResponse<>(ITEM_NO_COUNT);
        }
        try{
            GetCategoryRes getCategoryRes = itemProvider.getCategory(code, sort, count);
            return new BaseResponse<>(getCategoryRes);
        }catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
    @ResponseBody
    @PostMapping("")
    public BaseResponse<ItemRes> CreateItem(@RequestBody ItemReq itemReq) {
        if(itemReq.getImages().get(0).equals("") || itemReq.getImages().isEmpty()){
            return new BaseResponse<>(POST_ITEM_EMPTY_IMAGE);
        }
        try {
            validateItems(itemReq);
            ItemRes itemRes = itemService.createItem(itemReq);
            return new BaseResponse<>(itemRes);
        }
        catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{idx}")
    public BaseResponse<GetItemRes> PatchItem(@PathVariable("idx") Integer idx,
                                           @RequestBody ItemReq itemReq) {
        if(itemReq.getImages().get(0).equals("") || itemReq.getImages().isEmpty()){
            return new BaseResponse<>(POST_ITEM_EMPTY_IMAGE);
        }
        try {
            validateItems(itemReq);
            itemService.patchItem(idx,itemReq);
            return new BaseResponse<>(itemProvider.getItem(idx));
        }
        catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
    @ResponseBody
    @PostMapping("/{idx}")
    public BaseResponse<HashMap<String, String>> PatchItemStatus(@PathVariable("idx") Integer idx,
                                              @RequestBody Map<String, String> param) {

        char statusC = param.get("status").charAt(0);
        if(!(statusC == 'Y'||statusC=='R'||statusC=='S'||statusC=='D')){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            return new BaseResponse<>(itemService.PatchItemStatus(idx, param.get("status")));
        }
        catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

}

/*

    @ResponseBody
    @PostMapping("/{search}")
     public BaseResponse<PostSearchRes> PostSearch(@RequestBody PostSearchReq postSearchReq) {
        // 입력 없으면 int : 0, char:'\0', String:null, boolean:false
        if(postSearchReq.getCategory().length()>10){
            return new BaseResponse<>(ITEM_INVALID_CATEGORY);
        }
        if(postSearchReq.getBrand() < 0){
            return new BaseResponse<>(ITEM_INVALID_BRAND_ID);
        }
        if(postSearchReq.getMinPrice() < 0 ||
                postSearchReq.getMinPrice() > postSearchReq.getMaxPrice()){
            return new BaseResponse<>(ITEM_INVALID_PRICE_MIN);
        }
        if(postSearchReq.getMaxPrice() > 100000000){
            return new BaseResponse<>(ITEM_INVALID_PRICE_MAX);
        }
        char sort = postSearchReq.getSort();
        if(sort == '\0'){
            postSearchReq.setSort('R');
        }
        else if(sort!='R' && sort !='F' &&
                sort !='L' && sort !='H'){
            return new BaseResponse<>(ITEM_INVALID_SORT);
        }
        int period = postSearchReq.getPeriod();
        if( period!= 0 && period != 1 && period != 3 && period != 7 && period!= 30){
            return new BaseResponse<>(ITEM_INVALID_PERIOD);
        }
        char delivery = postSearchReq.getDelivery();
        if (delivery == '\0'){
            postSearchReq.setDelivery('E');
        }
        else if(delivery != 'Y' && delivery != 'N' && delivery != 'E'){
            return new BaseResponse<>(ITEM_INVALID_DELIVERY);
        }
        char isNew = postSearchReq.getIsNew();
        if (isNew == '\0'){
            postSearchReq.setIsNew('E');
        }
        else if(isNew != 'Y' && isNew != 'N' && isNew != 'E'){
            return new BaseResponse<>(ITEM_INVALID_DELIVERY);
        }
        char exchange = postSearchReq.getExchange();
        if (exchange == '\0'){
            postSearchReq.setExchange('E');
        }
        else if(exchange != 'Y' && exchange != 'N' && exchange != 'E'){
            return new BaseResponse<>(ITEM_INVALID_DELIVERY);
        }
        try {
            PostSearchRes postSearchRes = itemProvider.PostSearch(postSearchReq);
            return new BaseResponse<>(postSearchRes);
        } catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
    */

