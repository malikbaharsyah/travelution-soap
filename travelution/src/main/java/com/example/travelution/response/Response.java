package com.example.travelution.response;

//Base Response Class
public class Response {
    public boolean success;
    public String message;

    public Response() {
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
