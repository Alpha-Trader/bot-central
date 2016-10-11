package com.alphatrader.javagui.data;

import com.alphatrader.javagui.AppState;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bond in the game. Contains factory methods to handle json input as well.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class Bond {
  /**
   * Fetches all bonds currently on the market from the server.
   * @return all bonds on the market
   */
  public static List<Bond> getAllBonds() {
    List<Bond> myReturn = new ArrayList<>();

    try {
      HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/bonds/")
          .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
          .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();

      JSONArray bonds = response.getBody().getArray();

      for (int i = 0; i < bonds.length(); i++) {
        //System.out.println(bonds.getJSONObject(i).toString(2));
        myReturn.add(Bond.createFromJson(bonds.getJSONObject(i)));
      }

    } catch (UnirestException e) {
      System.err.println("Error fetching bonds : " + e.getMessage());
    }

    return myReturn;
  }

  /**
   * Creates a bond from the api json answers.
   * @param json the json object you want to parse.
   * @return the parsed bond
   */
  public static Bond createFromJson(JSONObject json) {
    Bond myReturn = new Bond(
        json.getString("name"),
        json.getInt("volume"),
        json.getDouble("interestRate"),
        json.getDouble("faceValue"),
        LocalDateTime.ofInstant(Instant.ofEpochMilli(json.getLong("maturityDate")), ZoneId.systemDefault())
    );

    return myReturn;
  }

  /**
   * The bond's name.
   */
  private String name;

  /**
   * The bond's volume.
   */
  private int volume;

  /**
   * The overall interest rate of the bond.
   */
  private double interestRate;

  /**
   * The face value the bond was issued for.
   */
  private double faceValue;

  /**
   * The bond's date of maturity.
   */
  private LocalDateTime maturityDate;

  /**
   * Creates a new bond with the given parameters.
   * @param name the name
   * @param volume the volume
   * @param interestRate the interest rate
   * @param faceValue the face value it was issued for
   * @param maturityDate the maturity date
   */
  public Bond (String name, int volume, double interestRate, double faceValue, LocalDateTime maturityDate) {
    this.name = name;
    this.volume = volume;
    this.interestRate = interestRate;
    this.faceValue = faceValue;
    this.maturityDate = maturityDate;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the volume
   */
  public int getVolume() {
    return volume;
  }

  /**
   * @return the interest rate
   */
  public double getInterestRate() {
    return interestRate;
  }

  /**
   * @return the face value
   */
  public double getFaceValue() {
    return faceValue;
  }

  /**
   * @return the maturity date
   */
  public LocalDateTime getMaturityDate() {
    return maturityDate;
  }

  @Override
  public String toString() {
    return "Bond{" +
        "name='" + name + '\'' +
        ", volume=" + volume +
        ", interestRate=" + interestRate +
        ", faceValue=" + faceValue +
        ", maturityDate=" + maturityDate +
        '}';
  }
}