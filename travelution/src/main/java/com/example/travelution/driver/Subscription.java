package com.example.travelution.driver;


import com.example.travelution.log.Logger;
import com.example.travelution.request.*;
import com.example.travelution.response.*;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

@WebService(endpointInterface = "travelution.driver.ISubscription")
public class Subscription implements ISubscription{
    @Resource
    WebServiceContext wsContext;

    private Connection db_conn;
    private Logger logger;

    private final Properties properties = new Properties();

    public Subscription() {

    }
    public Subscription(Connection db_conn) {
        this.db_conn = db_conn;
        this.logger = new Logger(db_conn);
        try (InputStream input = Files.newInputStream(Paths.get("../../../../../../../config.properties"))) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SubscriptionResp requestSubscription(SubscriptionReq reqSub) {
        LocalDateTime timestamp = LocalDateTime.now();
        String IPAddress = this.getRequestIPAddress();

        SubscriptionResp resp;

        if (!properties.getProperty("API_KEY").equals(reqSub.API_KEY)) {
            resp = new SubscriptionResp(false, "Not Authorized", null, null);
        } else {
            try {
                Statement statement = this.db_conn.createStatement();
                String sql = "INSERT INTO subscription(subscriber_id, status) "
                        + "VALUES (%d, '%s')";
                String formattedSql = String.format(sql, reqSub.subscriberId, "PENDING");
                int count = statement.executeUpdate(formattedSql);
                if (count == 1) {
                    resp = new SubscriptionResp(true, "Added new subscription request", reqSub.subscriberId, "PENDING");
                } else {
                    resp = new SubscriptionResp(true, "Subscription request failed", reqSub.subscriberId, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp = new SubscriptionResp(false, e.getMessage(), null, null);
            }
        }

        this.logger.generateLogging(timestamp, IPAddress, reqSub, resp);
        return resp;
    }

    @Override
    public SubscriptionApprovalResp approveOrRejectSubscription(SubscriptionApprovalReq appOrRej) {
        LocalDateTime timestamp = LocalDateTime.now();
        String IPAddress = this.getRequestIPAddress();

        SubscriptionApprovalResp resp;

        if (!properties.getProperty("API_KEY").equals(appOrRej.API_KEY)) {
            resp = new SubscriptionApprovalResp(false, "Not Authorized", null, null);
        } else {
            try {
                Statement statement = this.db_conn.createStatement();
                String sql =
                        "UPDATE subscription " +
                                "SET status = '%s' " +
                                "WHERE " +
                                "subscriber_id = %d";

                String appOrRejString = appOrRej.approve ? "ACCEPTED": "REJECTED";
                String formattedSql = String.format(sql, appOrRejString, appOrRej.subscriberId);
                int count = statement.executeUpdate(formattedSql);
                if (count == 1) {
                    String message = appOrRejString + " subscription successfully";
                    resp = new SubscriptionApprovalResp(true, message, appOrRej.subscriberId, appOrRejString);
                } else {
                    String message = appOrRejString + " subscription failed";
                    resp = new SubscriptionApprovalResp(false, message, appOrRej.subscriberId, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                resp = new SubscriptionApprovalResp(false, e.getMessage(), null, null);
            }
        }

        this.logger.generateLogging(timestamp, IPAddress, appOrRej, resp);
        return resp;
    }

    @Override
    public SubscriptionListResp listRequestSubscription(SubscriptionListReq listReqSub) {
        LocalDateTime timestamp = LocalDateTime.now();
        String IPAddress = this.getRequestIPAddress();

        SubscriptionListResp resp;

        if (!properties.getProperty("API_KEY").equals(listReqSub.API_KEY)) {
            resp = new SubscriptionListResp(false, "Not Authorized", null);
        } else {
            try {
                Statement statement = this.db_conn.createStatement();
                String sql = "SELECT * FROM subscription WHERE status = 'PENDING'";
                ResultSet resultSet = statement.executeQuery(sql);
                List<Subscriber> listResp = new ArrayList<>();
                while (resultSet.next()) {
                    listResp.add(new Subscriber(
                            resultSet.getInt("subscriber_id"),
                            resultSet.getString("status")
                    ));
                }
                String message = "Subscription request list fetched successfully";
                resp = new SubscriptionListResp(true, message, listResp);

            } catch (Exception e) {
                e.printStackTrace();
                resp = new SubscriptionListResp(false, e.getMessage(), null);
            }
        }

        this.logger.generateLogging(timestamp, IPAddress, listReqSub, resp);
        return resp;
    }

    @Override
    public ValidateSubscriptionResp validateSubscription(ValidateSubscriptionReq valSub) {
        LocalDateTime timestamp = LocalDateTime.now();
        String IPAddress = this.getRequestIPAddress();

        ValidateSubscriptionResp resp;

        if (!properties.getProperty("API_KEY").equals(valSub.API_KEY)) {
            resp = new ValidateSubscriptionResp(false, "Not Authorized", null, null);
        } else {
            try {
                Statement statement = this.db_conn.createStatement();
                String sql = "SELECT * FROM subscription WHERE subscriber_id = " + valSub.subscriberId;
                ResultSet resultSet = statement.executeQuery(sql);

                Subscriber subscription = null;
                if (resultSet.next()) {
                    subscription = new Subscriber(
                            resultSet.getInt("subscriber_id"),
                            resultSet.getString("status")
                    );
                }
                String message = "Subscription validation fetched successfully";
                boolean subscribed = subscription != null && subscription.status.equals("ACCEPTED");

                resp = new ValidateSubscriptionResp(true, message, valSub.subscriberId, subscribed);

            } catch (Exception e) {
                e.printStackTrace();
                resp = new ValidateSubscriptionResp(false, e.getMessage(), null, null);
            }
        }

        this.logger.generateLogging(timestamp, IPAddress, valSub, resp);
        return resp;
    }

    public String getRequestIPAddress() {
        MessageContext messageContext = wsContext.getMessageContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
        return httpServletRequest.getRemoteAddr();
    }
}
