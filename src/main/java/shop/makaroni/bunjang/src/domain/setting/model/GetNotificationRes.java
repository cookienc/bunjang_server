package shop.makaroni.bunjang.src.domain.setting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetNotificationRes {
    private Boolean alarmSet;
    private Boolean silentTime;
    private String silentStart;
    private String silentEnd;
    private Boolean chat;
    private Boolean itemComment;
    private Boolean itemWish;
    private Boolean itemDiscount;
    private Boolean reservedUp;
    private Boolean resell;
    private Boolean priceSuggestion;
    private Boolean storeComment;
    private Boolean storeFollow;
    private Boolean storeReview;
    private Boolean deliveryProcess;
    private Boolean deliveryDone;
    private Boolean event;
    private Boolean wishContact;
    private Boolean townEvent;


    public GetNotificationRes(boolean alarmSet, boolean silentTime, String silentStart, String silentEnd, boolean chat, boolean itemComment, boolean itemWish, boolean itemDiscount, boolean reservedUp, boolean resell, boolean priceSuggestion, boolean storeComment, boolean storeFollow, boolean storeReview, boolean deliveryProcess, boolean deliveryDone, boolean event, boolean wishContact, boolean townEvent) {
        this.alarmSet = alarmSet;
        this.silentTime = silentTime;
        this.silentStart = silentStart;
        this.silentEnd = silentEnd;
        this.chat = chat;
        this.itemComment = itemComment;
        this.itemWish = itemWish;
        this.itemDiscount = itemDiscount;
        this.reservedUp = reservedUp;
        this.resell = resell;
        this.priceSuggestion = priceSuggestion;
        this.storeComment = storeComment;
        this.storeFollow = storeFollow;
        this.storeReview = storeReview;
        this.deliveryProcess = deliveryProcess;
        this.deliveryDone = deliveryDone;
        this.event = event;
        this.wishContact = wishContact;
        this.townEvent = townEvent;
    }
}



































