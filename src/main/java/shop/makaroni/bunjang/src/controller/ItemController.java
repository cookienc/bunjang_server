package shop.makaroni.bunjang.src.controller;

import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.makaroni.bunjang.src.provider.ItemProvider;
import shop.makaroni.bunjang.src.service.ItemService;

import java.sql.Array;

@RestController
@RequestMapping("/item")
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
            GetItemRes getItemRes;
            getItemRes= itemProvider.getItem(itemIdx);
            getItemRes.setWish(itemProvider.getItemWishCnt(itemIdx));
            getItemRes.setChat(itemProvider.getItemChatCnt(itemIdx));
            getItemRes.setTags(itemProvider.getItemTags(itemIdx));
            getItemRes.setImages(itemProvider.getItemImages(itemIdx));
            return new BaseResponse<>(getItemRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
