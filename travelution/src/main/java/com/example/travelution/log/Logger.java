package com.example.travelution.log;

import com.example.travelution.request.*;
import com.example.travelution.response.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private Connection db_conn;

    public Logger(Connection db_conn) {
        this.db_conn = db_conn;
    }

    public void generateLogging(LocalDateTime timestamp, String IPAddress, Request req, Response resp) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder descriptionBuilder = new StringBuilder();
        String endpoint = "";

        if (!resp.success && resp.message.equals("Not Authorized")) {
            descriptionBuilder.append("request rejected");
        } else {
            if (req instanceof SubscriptionReq && resp instanceof SubscriptionResp) {
                SubscriptionReq reqCasted = (SubscriptionReq) req;
                descriptionBuilder.append("Request subscription for subscriber_id = " + reqCasted.subscriberId);
                endpoint = "Subscription Request ";

            } else if (req instanceof SubscriptionListReq && resp instanceof SubscriptionListResp) {
                descriptionBuilder.append("Request list of all subscription with status \"PENDING\"");
                endpoint = "Subscription List";

            } else if (req instanceof ValidateSubscriptionReq && resp instanceof ValidateSubscriptionResp) {
                ValidateSubscriptionReq reqCasted = (ValidateSubscriptionReq) req;
                ValidateSubscriptionResp respCasted = (ValidateSubscriptionResp) resp;
                descriptionBuilder.append(
                        "Request validate subscription for subscriber_id = " + reqCasted.subscriberId
                                + ", validation result is " + respCasted.subscribed
                );
                endpoint = "Validate Subscription";

            } else if (req instanceof SubscriptionApprovalReq && resp instanceof SubscriptionApprovalResp) {
                SubscriptionApprovalReq reqCasted = (SubscriptionApprovalReq) req;
                SubscriptionApprovalResp respCasted = (SubscriptionApprovalResp) resp;
                descriptionBuilder.append(
                        "Request to " + (reqCasted.approve ? "approve" : "reject") + " subscription request for subscriber_id = " + reqCasted.subscriberId
                                + ", subscription request " + respCasted.status
                );
                endpoint = "Subscription Approval";

            } else {
                descriptionBuilder.append("Invalid request format");
            }

            if (resp.success) {
                descriptionBuilder.append(", successful");
            } else {
                descriptionBuilder.append(", failed");
            }
        }
        try {
            String description = descriptionBuilder.toString();
            String timestampString = dtf.format(timestamp);
            Statement statement = this.db_conn.createStatement();
            String sql = "INSERT INTO logging(description, IP, endpoint, req_time) "
                    + "VALUES ('%s', '%s', '%s', '%s')";

            String formattedSql = String.format(sql, description, IPAddress, endpoint, timestampString);
            int count = statement.executeUpdate(formattedSql);
            if (count == 1) {
                System.out.println("Log added");
            } else {
                System.out.println("Failed to add log");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to add log");
        }
    }
}
