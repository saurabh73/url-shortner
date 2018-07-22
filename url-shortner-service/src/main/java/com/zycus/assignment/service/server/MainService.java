package com.zycus.assignment.service.server;

import com.zycus.assignment.core.URLShortner;
import com.zycus.assignment.core.exception.ConvertException;
import com.zycus.assignment.core.exception.FetchException;
import com.zycus.assignment.service.config.ServerConfig;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainService {

    private Service service;
    private static Logger LOGGER = LoggerFactory.getLogger(MainService.class);
    private URLShortner shortnerTask;

    public void start() {

        final String BASE_URL = "http://localhost:" + ServerConfig.PORT;
        LOGGER.info("Starting REST server " + BASE_URL);
        service = Service.ignite()
                .port(ServerConfig.PORT)
                .threadPool(ServerConfig.THREAD_POOL_SIZE);

        // Initialize Service
        this.shortnerTask = new URLShortner();

        // Routes
        LOGGER.info("Configuring CONVERT API " + BASE_URL + ServerConfig.CONVERT_ENDPOINT);
        service.post(ServerConfig.CONVERT_ENDPOINT, (request, response) -> {
            LOGGER.info("Request CONVERT {} ");
            List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
            Map<String, String> params = toMap(pairs);
            String url = params.get("url");
            if (url != null) {
                url =  URLDecoder.decode(url, "UTF-8");
                try {
                    response.status(200);
                    return this.shortnerTask.convert(url);
                } catch (FetchException ex) {
                    response.status(422);
                    return ex.getMessage();
                }
            } else {
                response.status(400);
                return "URL not present";
            }
        });

        LOGGER.info("Configuring FETCH API " + BASE_URL + ServerConfig.FETCH_ENDPOINT);
        service.get(ServerConfig.FETCH_ENDPOINT, (request, response) -> {
            LOGGER.info("Request FETCH {} ");
            String url = request.queryParams("url");
            if (url != null) {
                try {
                    LOGGER.info("Short URL "+ url);
                    response.status(200);
                    return this.shortnerTask.fetch(url);
                } catch (ConvertException ex) {
                    response.status(422);
                    return ex.getMessage();
                }
            } else {
                response.status(400);
                return "URL not present";
            }
        });

    }

    public void stop() {
        LOGGER.info("Stopping REST server service");
        if (service != null) {
            service.stop();
        }
    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for (NameValuePair pair : pairs) {
            LOGGER.info(pair.getName() +" " + pair.getValue());
            map.put(pair.getName(), pair.getValue());
        }
        return map;
    }
}
