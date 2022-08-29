package shop.makaroni.bunjang.src.domain.inquiry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteInquiryReq {
    private Long targetIdx;
    private Long parentIdx;
    private String type;
}
