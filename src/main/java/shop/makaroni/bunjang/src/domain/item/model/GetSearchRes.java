package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetSearchRes {
    private String itemIdx;
    private String price;
    private String name;
    private boolean safePay;
    private boolean isAd;
    private String image;
    private char status;
}
