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
    private boolean notification;
    private String category;
    private String location;
    private String minPrice;
    private String maxPrice;

    public Object[] getKeywords(Long userIdx){
        return new Object[]{userIdx, keyword, notification, category, location, minPrice, maxPrice};
    }
}
