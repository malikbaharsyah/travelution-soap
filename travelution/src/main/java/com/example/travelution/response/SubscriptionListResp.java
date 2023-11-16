package com.example.travelution.response;

import java.util.List;
//list subscriber yang masih pending
public class SubscriptionListResp extends Response{
    public SubscriptionListWrapper list;

    public SubscriptionListResp(boolean success, String message, List<Subscriber> list) {
        super(success, message);
        this.list = new SubscriptionListWrapper(list);
    }
}
