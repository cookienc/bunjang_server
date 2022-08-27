package shop.makaroni.bunjang.src.domain.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetWishListRes {
    private ItemPreview items;
    private int wishCnt;
    private List<WishList> wishList;
//    public GetWishListRes(ItemPreview items, int wishCnt, List<WishList> wishList)){
//        this.wishCnt = wishCnt;
//        this.wishList = wishList;
//        this.items = items;
//
//    }
}
