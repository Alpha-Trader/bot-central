package com.alphatrader.javagui.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Bond;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author frangelo
 */
public class Notification {
    /**
     * Fetches all unread events of the user and marks them as read
     *
     * @return all unread events
     */
    public static List<Notification> getUnreadNotifications() {
        List<Notification> myReturn = new ArrayList<>();

        try {
            HttpResponse<JsonNode> response = Unirest.get(AppState.getInstance().getApiUrl() + "/api/notifications/unread/")
                .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();


            JSONArray notifications = response.getBody().getArray();

            for (int i = 0; i < notifications.length(); i++) {
                myReturn.add(Notification.createFromJson(notifications.getJSONObject(i)));
            }

        } catch (UnirestException e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
        }

        return myReturn;
    }

    /**
     * Creates a Notification from the api json answers and marks it as read
     */
    public static Notification createFromJson(JSONObject json) {
        Notification myReturn = new Notification(
            LocalDateTime.ofInstant(Instant.ofEpochMilli(json.getLong("date")), ZoneId.systemDefault()),
            json.getJSONObject("content").getString("filledString")
        );
        return myReturn;
    }

    /**
     * Marks all notifications as read.
     */
    public static void markAllAsRead() {
        try {
            Unirest.put(AppState.getInstance().getApiUrl() + "/api/notifications/read/")
                .header("accept", "*/*").header("Authorization", "Bearer " + AppState.getInstance().getUser().getToken())
                .header("X-Authorization", "e1d149fb-0b2a-4cf5-9ef7-17749bf9d144").asJson();
        } catch (UnirestException ue) {
            System.err.println("Error marking notifications as read.");
            ue.printStackTrace();
        }
    }

    /**
     * The message of the notification.
     */
    private String message;

    /**
     * The date and time the notification was created.
     */
    private LocalDateTime date;

    /**
     * Creates a unread Notification with the given parameters
     *
     * @param date the date this notification was created
     * @param message the message.
     */
    public Notification(LocalDateTime date, String message) {
        this.date = date;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "message='" + message + '\'' +
            ", date=" + date +
            '}';
    }

    /**
     * @return the Message
     */
    public String getMessage() {
        return message;
    }

}


