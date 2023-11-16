package com.example.travelution.driver;

import com.example.travelution.request.*;
import com.example.travelution.response.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface ISubscription {
    @WebMethod(action = "\"requestSubscription\"")
    SubscriptionResp requestSubscription(
            @WebParam(name="request") SubscriptionReq subReq
    );

    @WebMethod(action = "\"approveOrRejectSubscription\"")
    SubscriptionApprovalResp approveOrRejectSubscription(
            @WebParam(name="request") SubscriptionApprovalReq subApprovalReq
    );

    @WebMethod(action = "\"listRequestSubscription\"")
    SubscriptionListResp listRequestSubscription(
            @WebParam(name="request") SubscriptionListReq subListReq
    );

    @WebMethod(action = "\"validateSubscription\"")
    ValidateSubscriptionResp validateSubscription(
            @WebParam(name="request") ValidateSubscriptionReq valSub
    );
}
