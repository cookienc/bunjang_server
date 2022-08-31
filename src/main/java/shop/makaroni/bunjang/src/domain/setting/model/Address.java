package shop.makaroni.bunjang.src.domain.setting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {
    private String idx;
    private String name;
    private String phoneNum;
    private String address;
    private String detail;
    private Boolean isDefault;

    public Object[] getAddress(Long idx){
        return new Object[]{name, phoneNum, address, detail, isDefault, idx};
    }
}
