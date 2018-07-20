package com.zycus.assignment.service;

import com.zycus.assignment.service.server.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        final MainService service = new MainService();
        startRestService(service);
    }

    private static void startRestService(MainService service) {
        try {
            service.start();
            LOGGER.info("REST server started");
        } catch (Exception e) {
            // Failure to start is associated with incorrect configuration or misbehaving dependencies
            exit("Error starting URL shortening: " + e.getMessage());
        }

        // Make sure disposable dependencies are gracefully stopped upon JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("JVM Shutdown Hook");
            service.stop();
        }));
    }


    private static void exit(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
