package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSearchReq {
    private String category;
    private int brand;
    private int minPrice;
    private int maxPrice;
    private boolean soldOut;
    private char sort;
    private int period;
    private char delivery;
    private char isNew;
    private char exchange;

    public boolean getSoldOut(){ return soldOut; }

}


