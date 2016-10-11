/**
 *
 */
package com.alphatrader.javagui.data;

import java.util.ArrayList;
import java.util.List;

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

            JSONArray listings = response.getBody().getArray();
            for (int i = 0; i < listings.length(); i++) {
                // System.out.println(bonds.getJSONObject(i).toString(2));
                myReturn.add(Stock.createFromJson(listings.getJSONObject(i)));
            }
        } catch (UnirestException e) {
            System.err.println("Error fetching bonds : " + e.getMessage());
        }
        return myReturn;
    }

    /**
     * Creates a Stock from the api json answers.
     *
     * @param json the json object you want to parse
     * @return the parsed stock
     */

    public static Stock createFromJson(JSONObject json) {
        Stock myReturn = new Stock(json.getString("name"), json.getString("securityIdentifier"),
            json.getInt("startDate"));
        return myReturn;
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
    private int startDate;

    /**
     * Creates a new Stock with the given Parameters
     *
     * @param name
     * @param securityIdentifier
     * @param startDate
     */
    public Stock(String name, String securityIdentifier, int startDate) {
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
    public int getStartDate() {
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
