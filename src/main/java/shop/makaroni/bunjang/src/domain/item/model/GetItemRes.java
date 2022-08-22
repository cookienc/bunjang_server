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
    private String hit;
    private String stock;
    private String wish;
    private String chat;
    private boolean isNew;
    private boolean delivery;
    private boolean exchange;
    private String content;
    private String category;
    private String brand;
    private String seller;
    private char status;
    private  List<String> tags;
    private  List<String> images;

}