package com.example.travelution.response;

//entity yang msk ke database
public class Subscriber {
    public int subscriberId;
    public String status;

    public Subscriber(int subscriberId, String status) {
        this.subscriberId = subscriberId;
        this.status = status;
    }
}

