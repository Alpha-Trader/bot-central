/**
 *
 */
package com.alphatrader.javagui;

import com.alphatrader.rest.Notification;
import com.alphatrader.rest.User;
import com.alphatrader.rest.util.ApiLibConfig;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the state of the app. Implemented as a singleton to make sure, every
 * class works with the same data.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class AppState {
    /**
     * The singleton instance.
     */
    private static AppState instance;

    /**
     * Returns the singleton instance of the app state and creates it, if necessary.
     *
     * @return the singleton instance.
     */
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    /**
     * Private constructor to avoid instantiation outside of the singleton class.
     */
    private AppState() {
    }

    /**
     * The current user.
     */
    private User user;

    /**
     * The url of the api.
     */
    private String apiUrl = "http://stable.alpha-trader.com";

    /**
     * A list of all notifications received since we started the app.
     */
    private ObservableList<Notification> notifications = FXCollections.observableArrayList();

    /**
     * @return the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Changes the logged in user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
        ApiLibConfig.getInstance().setUser(user);
    }

    /**
     * @return the url of the api endpoints
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * @return the observable list of notifications
     */
    public ObservableList<Notification> getNotifications() {
        return notifications;
    }
}
