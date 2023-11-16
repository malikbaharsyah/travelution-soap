package com.example.travelution.response;

//boolean isinya true kalo dia subscribe
public class ValidateSubscriptionResp extends Response{
    public Integer subscriberId;
    public Boolean subscribed;

    public ValidateSubscriptionResp(boolean success, String message, Integer subscriberId, Boolean subscribed) {
        super(success, message);
        this.subscriberId = subscriberId;
        this.subscribed = subscribed;
    }
}
