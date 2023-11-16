package com.example.travelution;

import com.example.travelution.driver.Subscription;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Properties;

import javax.xml.ws.Endpoint;

public class Web {
    public static void main( String[] args ) throws IOException, InterruptedException {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("../../../../../../../config.properties"))) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DB db = new DB();
        Connection db_conn = db.getConnection();

        Endpoint.publish(properties.getProperty("BASE_URL") + "/subscription", new Subscription(db_conn));
    }
}