package com.alphatrader.javagui.data;

import com.alphatrader.javagui.AppState;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a single portfolio position in the game.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class Position {
    /**
     * Creates a new portfolio position from an API JSON response.
     *
     * @param json the json to parse
     * @return the parsed position object
     */
    public static Position createFromJson(JSONObject json) {
        Position myReturn = null;
        try {
            new Position(
                json.getString("securityIdentifier"),
                json.getInt("numberOfShares"),
                json.getDouble("volume")
            );
        } catch (JSONException je) {
            System.out.print(json.toString(2));
            je.printStackTrace();
        }

        return myReturn;
    }

    /**
     * The security identifier of this position.
     */
    private String securityIdentifier;

    /**
     * The price this position was traded for in the last trade.
     */
    private double lastPrice;

    /**
     * The number of shares held in the portfolio.
     */
    private int numberOfShares;

    /**
     * The overall volume of this position.
     */
    private double volume;

    /**
     * Creates a new position object with the given parameters.
     *
     * @param securityIdentifier the security identifier
     * @param numberOfShares     the number of shares
     * @param volume             the overall volume
     */
    public Position(String securityIdentifier, int numberOfShares, double volume) {
        this.securityIdentifier = securityIdentifier;
        this.lastPrice = volume / (double) numberOfShares;
        this.numberOfShares = numberOfShares;
        this.volume = volume;
    }

    /**
     * @return the security identifier
     */
    public String getSecurityIdentifier() {
        return securityIdentifier;
    }

    /**
     * @return the last trading price
     */
    public double getLastPrice() {
        return lastPrice;
    }

    /**
     * @return the number of shares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * @return the overall volume
     */
    public double getVolume() {
        return volume;
    }

    /**
     * @return the estimated value based on our own iterations.
     */
    public Double getEstimatedValue() {
        Double myReturn = AppState.getInstance().getValuationMap().get(this.getSecurityIdentifier());

        if(myReturn == null || myReturn.isNaN()) {
            myReturn = this.getLastPrice();
        }

        return myReturn * this.getNumberOfShares();
    }
}
