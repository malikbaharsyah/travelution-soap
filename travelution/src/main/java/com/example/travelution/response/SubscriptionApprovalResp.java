package com.example.travelution.response;

//status accepted atau rejected
public class SubscriptionApprovalResp extends Response{
    public Integer subscriberId;
    public String status;

    public SubscriptionApprovalResp(boolean success, String message, Integer subscriberId, String status) {
        super(success, message);
        this.subscriberId = subscriberId;
        this.status = status;
    }
}
