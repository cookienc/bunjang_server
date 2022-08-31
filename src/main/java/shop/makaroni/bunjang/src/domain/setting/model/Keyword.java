package shop.makaroni.bunjang.src.domain.setting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Keyword {
    private String idx;
    private String keyword;
    private Boolean notification;
    private String category;
    private String location;
    private String minPrice;
    private String maxPrice;
}
