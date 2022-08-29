package shop.makaroni.bunjang.src.controller;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;
import shop.makaroni.bunjang.src.service.ItemService;
import shop.makaroni.bunjang.src.domain.item.model.*;
import shop.makaroni.bunjang.utils.JwtService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;
import static shop.makaroni.bunjang.utils.Itemvalidation.validation.*;


@Transactional
@RestController
@RequestMapping("/items")
public class ItemController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final ItemProvider itemProvider;
    @Autowired
    private final ItemService itemService;
    @Autowired
    JwtService jwtService;

    public ItemController(ItemProvider itemProvider, ItemService itemService, JwtService jwtService) {
        this.itemProvider = itemProvider;
        this.itemService = itemService;
        this.jwtService = jwtService;
    }


    @ResponseBody
    @GetMapping("/{itemIdx}")
    public BaseResponse<GetItemRes> getItem(@PathVariable("itemIdx") Long itemIdx) {
        try {
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
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
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>(itemProvider.getItems());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetSearchRes>> getSearch(@RequestParam() String name,
                                                      @RequestParam(required = false, defaultValue = "C") char sort,
                                                      @RequestParam() int count) {
        try {
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (name == null) {
                throw new BaseException(ITEM_NO_NAME);
            }
            if (count == 0) {
                throw new BaseException(ITEM_NO_COUNT);
            }
            if (sort != 'C' && sort != 'R' && sort != 'L' && sort != 'H') {
                throw new BaseException(ITEM_INVALID_SORT);
            }
            return new BaseResponse<>(itemProvider.getSearch(name, sort, count));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/last")
    public BaseResponse<List<GetLogRes>> getItemLastN(@RequestParam() int count) {

        if (count <= 0) {
            return new BaseResponse<>(ITEM_NO_COUNT);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(itemProvider.getItemLastN(userIdx, count));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/brand/follow")
    public BaseResponse<List<GetUserBrandRes>> getUserBrand(@RequestParam(required = false, defaultValue = "K") char sort) {


        if (!(sort == 'K' || sort == 'E')) {
            return new BaseResponse<>(ITEM_INVALID_SORT);
        }
        List<GetUserBrandRes> getUserBrandRes;
        Long brandIdx;
        try {
            Long userIdx = jwtService.getUserIdx();
            if (userIdx <= 0) {
                return new BaseResponse<>(USERS_INVALID_IDX);
            }
            getUserBrandRes = itemProvider.getUserBrand(userIdx, sort);
            for (GetUserBrandRes eachRes : getUserBrandRes) {
                brandIdx = Long.parseLong(eachRes.getBrandIdx());
                eachRes.setItemCnt(String.valueOf(itemProvider.getItemCnt(brandIdx)));
            }
            return new BaseResponse<>(getUserBrandRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/brand")
    public BaseResponse<List<GetBrandRes>> getBrand(@RequestParam(required = false, defaultValue = "K") char sort) throws BaseException {

        if (!(sort == 'K' || sort == 'E')) {
            return new BaseResponse<>(ITEM_INVALID_SORT);
        }
        List<GetBrandRes> getBrandRes;
        Long brandIdx;
        try {
            Long userIdx = jwtService.getUserIdx();
            getBrandRes = itemProvider.getBrand(userIdx, sort);
            for (GetBrandRes eachRes : getBrandRes) {
                brandIdx = Long.parseLong(eachRes.getBrandIdx());
                eachRes.setItemCnt(String.valueOf(itemProvider.getItemCnt(brandIdx)));
            }
            return new BaseResponse<>(getBrandRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }


    }

    @ResponseBody
    @GetMapping("/brand/search")
    public BaseResponse<List<GetBrandRes>> getBrandSearch(@RequestParam(required = false) String name) {

        ArrayList blank = new ArrayList();
        List<GetBrandRes> getBrandRes;
        Long brandIdx;
        try {
            Long userIdx = jwtService.getUserIdx();
            if (userIdx <= 0) {
                return new BaseResponse<>(USERS_INVALID_IDX);
            }
            if (name == null) {
                return new BaseResponse<>(blank);
            }
            getBrandRes = itemProvider.getBrandSearch(userIdx, name);
            for (GetBrandRes eachRes : getBrandRes) {
                brandIdx = Long.parseLong(eachRes.getBrandIdx());
                eachRes.setItemCnt(String.valueOf(itemProvider.getItemCnt(brandIdx)));
            }
            return new BaseResponse<>(getBrandRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/category/{code}")
    public BaseResponse<GetCategoryRes> getCategory(@PathVariable("code") String code,
                                                    @RequestParam(required = false, defaultValue = "R") char sort,
                                                    @RequestParam() Integer count) {

        if (code.length() > 10 || code.length() < 1) {
            return new BaseResponse<>(ITEM_INVALID_CATEGORY);
        }
        if (code.length() != 1 && code.length() % 2 != 0) {
            return new BaseResponse<>(ITEM_INVALID_CATEGORY);
        }
        if (count != null && count < 0) {
            return new BaseResponse<>(ITEM_NO_COUNT);
        }
        try {
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetCategoryRes getCategoryRes = itemProvider.getCategory(code, sort, count);
            return new BaseResponse<>(getCategoryRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/sellers")
    public BaseResponse<ItemRes> CreateItem(@RequestBody ItemReq itemReq) {
        if (itemReq.getImages().get(0).equals("") || itemReq.getImages().isEmpty()) {
            return new BaseResponse<>(POST_ITEM_EMPTY_IMAGE);
        }
        try {
            Long sellerIdx = jwtService.getUserIdx();
            validateItems(itemReq);
            ItemRes itemRes = itemService.createItem(sellerIdx, itemReq);
            return new BaseResponse<>(itemRes);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{idx}/sellers")
    public BaseResponse<GetItemRes> PatchItem(@PathVariable("idx") Long idx,
                                              @RequestBody ItemReq itemReq) {
        if (itemReq.getImages() != null && (itemReq.getImages().isEmpty() || itemReq.getImages().get(0).equals(""))) {
            return new BaseResponse<>(POST_ITEM_EMPTY_IMAGE);
        }
        try {
            Long sellerIdx = jwtService.getUserIdx();
            validateItems(itemReq);
            itemService.patchItem(idx,sellerIdx, itemReq);
            return new BaseResponse<>(itemProvider.getItem(idx));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{idx}/status/sellers")
    public BaseResponse<HashMap<String, String>> PatchItemStatus(@PathVariable("idx") Long idx,
                                                                 @RequestBody Map<String, String> param) {

        char statusC = param.get("status").charAt(0);
        if (!(statusC == 'Y' || statusC == 'R' || statusC == 'S' || statusC == 'D')) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(itemService.PatchItemStatus(idx, userIdx, param.get("status")));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @DeleteMapping("/{idx}/sellers")
    public BaseResponse<HashMap<String, String>> DeleteItem(@PathVariable("idx") Long idx) {
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(itemService.PatchItemStatus(idx,userIdx, "D"));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }

    }

    @ResponseBody
    @GetMapping("/{idx}/wishers")
    public BaseResponse<GetWisherRes> GetWishers(@PathVariable("idx") Long idx) {
        try {
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return itemProvider.getWisher(idx);
        } catch (BaseException baseException) {
                    return new BaseResponse<>(baseException.getStatus());
        }

    }

    @ResponseBody
    @PostMapping("/wish-lists")
    public BaseResponse<HashMap<String, String>> PostWish(@RequestBody Map<String, Long> param) {
        Long itemIdx = param.get("itemIdx");
        if (itemIdx < 0) {
            return new BaseResponse<>(ITEM_NO_EXIST);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(itemService.PostWish(itemIdx, userIdx));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @DeleteMapping("/wish-lists")
    public BaseResponse<HashMap<String, String>> PostWish(@RequestParam() Long itemIdx) {
        if (itemIdx < 0) {
            return new BaseResponse<>(ITEM_NO_EXIST);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(itemService.DeleteWish(itemIdx, userIdx));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/wish-lists")
    public BaseResponse<List<GetWishListRes>> getWishList()
            throws BaseException {

        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(itemProvider.getWishList(userIdx));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<GetSearchWordRes> getSearchWord(@RequestParam() String q)
            throws BaseException {
        if (q.isEmpty()) {
            return new BaseResponse<>(EMPTY_SEARCH_WORD);
        }
        if(q.length() > 4096){
            return new BaseResponse<>(INVALID_SEARCH_WORD);
        }
        try {
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>(itemProvider.getSearchWord(q));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }


    @ResponseBody
    @GetMapping("/deals")
    public BaseResponse<List<GetDealRes>> getDeal(@RequestParam() String tab,
                                                  @RequestParam(required = false, defaultValue = "E") String status)
            throws BaseException {
        if (!(tab.equals("order") || tab.equals("sale"))) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
        if(!("EPSF".contains(status))){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            logger.info(String.valueOf(userIdx));
            return new BaseResponse<>(itemProvider.getDeals(userIdx, tab, status));
        }
        catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/reports")
    public BaseResponse PostReport(@RequestBody PostReportReq postReportReq) {
        if(!(postReportReq.getType().charAt(0) == 'I' || postReportReq.getType().charAt(0) == 'R')){
            return new BaseResponse<>(POST_REPORT_INVALID_TYPE);
        }
        if(postReportReq.getTarget() < 0){
            return new BaseResponse(POST_REPORT_INVALID_TARGET);
        }
        if(postReportReq.getContent().length() < 1 || postReportReq.getContent().length() > 100 ){
            return new BaseResponse(POST_REPORT_CONTENT_LENGTH);
        }
        try{
            Long userIdx = jwtService.getUserIdx();
            itemService.postReport(userIdx, postReportReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
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

