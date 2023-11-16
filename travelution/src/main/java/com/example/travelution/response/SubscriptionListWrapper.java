package com.example.travelution.response;

import java.util.List;
public class SubscriptionListWrapper {

    public List<Subscriber> elements;
    public SubscriptionListWrapper(){

    }
    public SubscriptionListWrapper(List<Subscriber> elements) {
        this.elements = elements;
    }


}
