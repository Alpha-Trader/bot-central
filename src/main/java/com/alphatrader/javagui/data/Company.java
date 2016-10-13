package com.alphatrader.javagui.data;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import com.alphatrader.javagui.AppState;
import org.json.JSONArray;
import org.json.JSONException;
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

    public static List<Company> getAllCompanies() {
        List<Company> myReturn = new ArrayList<>();
        try {
            long start = System.nanoTime();
            HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/companies/all/")
                .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();
            long end1 = System.nanoTime();

            JSONArray companyNodes = response.getBody().getArray();
            long end2 = System.nanoTime();

            for (int i = 0; i < companyNodes.length(); i++) {
                myReturn.add(Company.createFromJson(companyNodes.getJSONObject(i)));
            }
            long end3 = System.nanoTime();

            System.out.println("API-Call: " + (end1 - start) + "ns\nGetting array: " + (end2 - end1) + "ns\nParsing: "
                + (end3 - end2) + "ns");

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
        Company myReturn = null;
        try {
            HttpResponse<JsonNode> response = Unirest.get(
                AppState.getInstance().getApiUrl() + "/api/companyprofiles/" + json.getString("id"))
                .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();


            int outstandingShares = response.getBody().getObject().getInt("outstandingShares");

            myReturn = new Company(
                json.getString("id"),
                json.getString("name"),
                json.getJSONObject("listing")
                    .getString("securityIdentifier"),
                json.getString("securitiesAccountId"),
                json.getJSONObject("bankAccount").getDouble("cash"),
                outstandingShares
            );
        } catch (JSONException je) {
            je.printStackTrace();
            System.out.println(json.toString(2));
        } catch (UnirestException ue) {
            ue.printStackTrace();
        }
        return myReturn;
    }

    /**
     * The unique company identifier.
     */
    private final String id;

    /**
     * The company name.
     */
    private final String name;

    /**
     * The security identifier of this company's stocks on the market.
     */
    private final String securityIdentifier;

    /**
     * The securities account id.
     */
    private final String securitiesAccountId;

    /**
     * The current amount of uncommitted cash.
     */
    private double cash;

    /**
     * The company's portfolio.
     */
    private Portfolio portfolio;

    /**
     * The number of company shares in circulation.
     */
    private int outstandingShares;

    /**
     * Creates a new Company object with the given parameters
     *
     * @param id                  the unique company id
     * @param name                the company name
     * @param securityIdentifier  the security identifier
     * @param securitiesAccountId the securities account id
     * @param cash                the amount of uncommitted cash
     */
    public Company(String id,
                   String name,
                   String securityIdentifier,
                   String securitiesAccountId,
                   double cash,
                   int outstandingShares) {
        this.id = id;
        this.name = name;
        this.securityIdentifier = securityIdentifier;
        this.securitiesAccountId = securitiesAccountId;
        this.cash = cash;
        this.outstandingShares = outstandingShares;
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
     * @return the securities accounts unique identifier.
     */
    public String getSecuritiesAccountId() {
        return securitiesAccountId;
    }

    /**
     * @return the company's cash
     */
    public double getCash() {
        return cash;
    }

    /**
     * @return the company's unique id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the portfolio of this company. This call will be evaluated lazily to avoid congesting the server.
     *
     * @return the company's portfolio.
     */
    public Portfolio getPortfolio() {
        if (this.portfolio == null) {
            this.portfolio = Portfolio.getCompanyPortfolio(this);
        }
        return this.portfolio;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Company [name=" + name + ", securityIdentifier=" + securityIdentifier + "]";
    }

    /**
     * @return the estimated value of this company
     */
    public Double getEstimatedStockValue() {
        // TODO fix iterative flattening.

        Map<String, Double> companyValuation = AppState.getInstance().getValuationMap();

        Double myReturn = Double.NaN;
        if (myReturn.isNaN()) {
            myReturn = this.getPortfolio().getEstimatedValue() / (double) this.outstandingShares;

            if (!myReturn.equals(companyValuation.get(this.getSecurityIdentifier()))) {
                System.out.println("Correcting valuation from " + companyValuation.get(this.getSecurityIdentifier()) + " to " + myReturn);
            }

            companyValuation.put(this.getSecurityIdentifier(), myReturn);
        } else {
            //System.out.println("Using cached value!");
        }

        return myReturn;
    }
}
