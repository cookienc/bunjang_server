package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetItemRes{
    private String price;
    private String name;
    private String location;
    private String time;
    private int hit;
    private int wish;
    private int chat;
    private boolean isNew;
    private boolean delivery;
    private boolean exchange;
    private String content;
    private String category;
    private int brand;
    private int seller;
    private char status;
    private  List<String> tags;
    private  List<String> images;

}