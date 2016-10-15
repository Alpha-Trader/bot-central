/**
 *
 */
package com.alphatrader.javagui.data;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.alphatrader.javagui.data.util.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alphatrader.javagui.AppState;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Represents a stock listing on the market.
 *
 * @author Frangelo Acampora (frangelo.acampora@gmail.com)
 * @version 1.0
 */
public class Stock {
    /**
     * Gson instance for deserialization.
     */
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();

    /**
     * List type for gson deserialization.
     */
    private static final Type listType = new TypeToken<ArrayList<Stock>>(){}.getType();

    /**
     * Fetches all listings currently on the market from the server.
     *
     * @return all listings on the market
     */
    public static List<Stock> getAllStocks() {
        List<Stock> myReturn = new ArrayList<>();

        try {
            HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/listings/")
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();

            String listings = response.getBody().getArray().toString();
            myReturn = gson.fromJson(listings, listType);
        } catch (UnirestException e) {
            System.err.println("Error fetching bonds : " + e.getMessage());
        }
        return myReturn;
    }

    /**
     * Creates a Stock from the api json answers.
     * corrected
     * @param json the json object you want to parse
     * @return the parsed stock
     */
    public static Stock createFromJson(String json) {
        return gson.fromJson(json, Stock.class);
    }

    /**
     * The stock's name
     */
    private String name;

    /**
     * The stock's security identifier
     */
    private String securityIdentifier;

    /**
     * The stock's issue date
     */
    private LocalDateTime startDate;

    /**
     * Creates a new Stock with the given Parameters
     *
     * @param name
     * @param securityIdentifier
     * @param startDate
     */
    public Stock(String name, String securityIdentifier, LocalDateTime startDate) {
        this.name = name;
        this.securityIdentifier = securityIdentifier;
        this.startDate = startDate;
    }

    /**
     * @return the stock listing's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the stock listing's security identifier
     */
    public String getSecurityIdentifier() {
        return securityIdentifier;
    }

    /**
     * @return the date the listing was issued
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * @return a string representation of the stock listing
     */
    public String toString() {
        return "Stock{" + "name='" + name + '\'' + ", securityIdentifier=" + securityIdentifier + ", startDate="
            + startDate + '}';
    }
}
