package com.zycus.assignment.service.server;

import com.zycus.assignment.service.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

public class MainService {

    private Service service;
    private static Logger LOGGER = LoggerFactory.getLogger(MainService.class);

    public void start() {

        final String BASE_URL = "http://localhost:" + ServerConfig.PORT;
        LOGGER.info("Starting REST server " + BASE_URL);
        service = Service.ignite()
                .port(ServerConfig.PORT)
                .threadPool(ServerConfig.THREAD_POOL_SIZE);

        // Routes
        LOGGER.info("Configuring CONVERT API " + BASE_URL + ServerConfig.CONVERT_ENDPOINT);
        service.get(ServerConfig.CONVERT_ENDPOINT, (request, response) -> {
            response.status(200);
            return "CONVERT";
        });

        LOGGER.info("Configuring FETCH API " + BASE_URL + ServerConfig.FETCH_ENDPOINT);
        service.get(ServerConfig.FETCH_ENDPOINT, (request, response) -> {
            response.status(200);
            return "FETCH";
        });

    }

    public void stop() {
        LOGGER.info("Stopping REST server service");
        if (service != null) {
            service.stop();
        }
    }
}
