package shop.makaroni.bunjang.src.domain.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetAddrressRes {
    private String count;
    List<Address> addressList;
}
