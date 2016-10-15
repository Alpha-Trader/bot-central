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
 * @author frangelo
 */
public class UnfilledOrder {
    /**
     * Listing class necessary for gson deserialization.
     *
     * @author Christopher Guckes
     * @version 1.0
     */
    private static final class Listing {
        /**
         * The name of the company which shares you are buying.
         */
        String name;
    }

    /**
     * Gson instance for deserialization.
     */
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();

    /**
     * List type for gson deserialization.
     */
    private static final Type listType = new TypeToken<ArrayList<UnfilledOrder>>(){}.getType();

    /**
     * Fetches all unfilled orders  of the user
     *
     * @return all unfilled orders
     */
    public static List<UnfilledOrder> getUnfilledOrders(Company company) {
        List<UnfilledOrder> myReturn = new ArrayList<>();

        try {
            HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/securityorders/securitiesaccount/" + company.getSecuritiesAccountId())
                .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();

            String unfilledOrders = response.getBody().getArray().toString();
            myReturn = gson.fromJson(unfilledOrders, listType);

        } catch (UnirestException e) {
            System.err.println("Error fetching unfilled Orders: " + e.getMessage());
        }

        return myReturn;
    }

    /**
     * Creates a UnfilledOrder from the api json answers
     */
    public static UnfilledOrder createFromJson(String json) {
        return gson.fromJson(json, UnfilledOrder.class);
    }

    /**
     * The date and time the order was created
     */
    private LocalDateTime creationDate;

    /**
     * Name of Security
     */
    private Listing listing = new Listing();
    /**
     * Type of Security
     */
    private String type;
    /**
     * Unique securityIdentifier of security
     */
    private String securityIdentifier;
    /**
     * Number of shares entailed in Order
     */
    private int numberOfShares;
    /**
     * Volume of Order
     */
    private double volume;

    public UnfilledOrder(LocalDateTime creationDate, String name, String securityIdentifier, String type, int numberOfShares) {
        this.creationDate = creationDate;
        this.listing.name = name;
        this.type = type;
        this.numberOfShares = numberOfShares;
        this.securityIdentifier = securityIdentifier;
    }

    @Override
    public String toString() {
        return "UnfilledOrder{" +
            "name='" + listing.name + '\'' +
            ", creationDate=" + creationDate +
            ", type=" + type +
            ", volume=" + volume +
            ", numberOfShares=" + numberOfShares +
            ", securityIdentifier=" + securityIdentifier +
            '}';
    }


    /**
     * @return the name
     */
    public String getName() {
        return listing.name;
    }

    /**
     * return the date of creation
     */
    public LocalDateTime getCreationDate() {

        return creationDate;
    }

    /**
     * @return the type of security
     */
    public String getType() {
        return type;
    }

    public String getSecurityIdentifier() {
        return securityIdentifier;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }
}


    
    
    



