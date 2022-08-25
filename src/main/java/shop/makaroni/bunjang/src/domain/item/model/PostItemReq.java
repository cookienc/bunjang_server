package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostItemReq {
    private List<String> images;
    private String name;
    private String category = "E";
    private List<String> tags;
    private int price;
    private boolean delivery;
    private int stock;
    private boolean isNew;
    private boolean exchange;
    private String content;
    private boolean safePay;
    private int sellerIdx;
    private String location;
    private boolean isAd;
    private boolean inspection;

    public Object[] getPostItemReq(){
        return new Object[]{sellerIdx, name, category, 0, price, delivery, content, stock, isNew,
                exchange, safePay, inspection, location, isAd};
    }
}
