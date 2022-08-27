package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetWishListRes {
    private String itemIdx;
    private String image;
    private String name;
    private String price;
    private String safePay;
    private String storeName;
    private String storeImg;
    private String updatedAt;
}
