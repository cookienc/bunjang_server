package shop.makaroni.bunjang.src.domain.setting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {
    private Boolean NA00;
    private Boolean NA01;
    private String NA0100;
    private String NA0101;
    private Boolean NB00;
    private Boolean NC00;
    private Boolean NC01;
    private Boolean NC02;
    private Boolean NC03;
    private Boolean NC04;
    private Boolean NC05;
    private Boolean ND00;
    private Boolean ND01;
    private Boolean ND02;
    private Boolean NE00;
    private Boolean NE01;
    private Boolean NF00;
    private Boolean NG00;
    private Boolean NG01;


    public Notification(Boolean NA00, Boolean NA01, String NA0100,
                        String NA0101, Boolean NB00, Boolean NC00,
                        Boolean NC01, Boolean NC02, Boolean NC03,
                        Boolean NC04, Boolean NC05, Boolean ND00,
                        Boolean ND01, Boolean ND02, Boolean NE00,
                        Boolean NE01, Boolean NF00, Boolean NG00,
                        Boolean NG01) {
        this.NA00 = NA00;
        this.NA01 = NA01;
        this.NA0100 = NA0100;
        this.NA0101 = NA0101;
        this.NB00 = NB00;
        this.NC00 = NC00;
        this.NC01 = NC01;
        this.NC02 = NC02;
        this.NC03 = NC03;
        this.NC04 = NC04;
        this.NC05 = NC05;
        this.ND00 = ND00;
        this.ND01 = ND01;
        this.ND02 = ND02;
        this.NE00 = NE00;
        this.NE01 = NE01;
        this.NF00 = NF00;
        this.NG00 = NG00;
        this.NG01 = NG01;
    }

    public Object[] getNotification(Long userIdx) {
        return new Object[]{NA00, NA01, NA0100, NA0101, NB00, NC00,
                NC01, NC02, NC03, NC04, NC05, ND00, ND01, ND02,
                NE00, NE01, NF00, NG00, NG01, userIdx};
    }
}







