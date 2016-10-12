package com.alphatrader.javagui.data;

import java.util.ArrayList;
import java.util.List;

import com.alphatrader.javagui.AppState;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Represents a company in the game. Contains factory methods for parsing api json answers as well.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class Company {
    /**
     * Fetches all companies currently employing the given user as a CEO.
     *
     * @param user the user who governs the company
     * @return a list of all companies governed by the user
     */
    public static List<Company> getAllUserCompanies(User user) {
        List<Company> myReturn = new ArrayList<>();

        try {
            HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/companies/")
                .header("accept", "*/*").header("Authorization", "Bearer " + user.getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();

            JSONArray companyNodes = response.getBody().getArray();

            for (int i = 0; i < companyNodes.length(); i++) {
                myReturn.add(Company.createFromJson(companyNodes.getJSONObject(i)));
            }
        } catch (UnirestException e) {
            System.err.println("Error fetching companies: " + e.getMessage());
        }

        return myReturn;
    }
    /**
     * fetches all companies in the game
     */
    
    public static List<Company> getAllCompanies(){
    	List<Company> myReturn = new ArrayList<>();
    try {
        HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/companies/all")
            .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
            .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();


        JSONArray companyNodes = response.getBody().getArray();

        for (int i = 0; i < companyNodes.length(); i++) {
            myReturn.add(Company.createFromJson(companyNodes.getJSONObject(i)));
        }

    } catch (UnirestException e) {
        System.err.println("Error fetching companies: " + e.getMessage());
    }

    return myReturn;
    }



    /**
     * Creates a Company object from the API's json response.
     *
     * @param json the json object you want to parse
     * @return the parsed company
     */
    public static Company createFromJson(JSONObject json) {
        Company myReturn = new Company(
            json.getString("name"),
            json.getJSONObject("listing")
                .getString("securityIdentifier"),
            json.getJSONObject("bankAccount").getDouble("cash")
        );
        return myReturn;
    }

    /**
     * The company name.
     */
    private final String name;

    /**
     * The security identifier of this company's stocks on the market.
     */
    private final String securityIdentifier;

    /**
     * The current amount of uncommitted cash.
     */
    private double cash;

    /**
     * Creates a new Company object with the given parameters
     *
     * @param name               the company name
     * @param securityIdentifier the security identifier
     */
    public Company(String name, String securityIdentifier, double cash) {
        this.name = name;
        this.securityIdentifier = securityIdentifier;
        this.cash = cash;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the securityIdentifier
     */
    public String getSecurityIdentifier() {
        return securityIdentifier;
    }

    /**
     * @return the company's cash
     */
    public double getCash() {
        return cash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Company [name=" + name + ", securityIdentifier=" + securityIdentifier + "]";
    }
}
