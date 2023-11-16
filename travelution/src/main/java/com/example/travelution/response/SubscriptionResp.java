package com.example.travelution.response;

//nambahin subscriber dgn status pending
public class SubscriptionResp extends Response{
    public Integer subscriberId;
    public String status;

    public SubscriptionResp() {
        super();
    }
    public SubscriptionResp(boolean success, String message, Integer subscriberId, String status) {
        super(success, message);
        this.subscriberId = subscriberId;
        this.status = status;
    }
}
