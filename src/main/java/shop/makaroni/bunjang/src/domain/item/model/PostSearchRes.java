package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSearchRes {
    private int price;
    private String name;
    private boolean wish;
}
