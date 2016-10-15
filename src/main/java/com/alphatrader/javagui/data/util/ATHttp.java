package com.alphatrader.javagui.data.util;

import com.alphatrader.javagui.AppState;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Wrapper to allow testing of all webservice classes.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class ATHttp {
    private static ATHttp instance = new ATHttp();

    public static void setInstance(ATHttp atHttp) {
        instance = atHttp;
    }

    public static ATHttp getInstance() {
        return instance;
    }

    public HttpResponse<JsonNode> get(String url) throws UnirestException {
        return Unirest.get(AppState.getInstance().getApiUrl() + url)
            .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
            .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();
    }

    public HttpResponse<JsonNode> post(String url) throws UnirestException {
        return Unirest.post(AppState.getInstance().getApiUrl() + url)
            .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
            .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();
    }
}
