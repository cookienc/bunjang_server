package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ItemReq {
    private List<String> images;
    private String name;
    private String category;
    private List<String> tags;
    private Integer price;
    private Integer delivery;
    private Integer stock;
    private Integer isNew;
    private Integer exchange;
    private String content;
    private Integer safePay;
    private String location;
    private Integer isAd;
    private Integer inspection;

    public Object[] getPostItemReq(Long sellerIdx){
        return new Object[]{sellerIdx, name, category, 0, price, delivery, content, stock, isNew,
                exchange, safePay, inspection, location, isAd, 0};
    }
}
