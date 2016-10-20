package com.alphatrader.javagui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import com.alphatrader.javagui.control.BackgroundDaemon;
import com.alphatrader.javagui.gui.LoginDialog;
import com.alphatrader.rest.User;
import com.alphatrader.rest.util.ApiLibConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Starts the app.
 *
 * @author christopher.guckes@torq-dev.de
 * @version 1.0
 */
public class BotCentral extends Application {
    /**
     * Launches the JavaFX app. Shouldn't do anything else!
     *
     * @param args commandline parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This is where the action takes place.
     *
     * @param stage the primary stage for our GUI
     * @throws Exception whenever it feels like it
     */
    @Override
    public void start(final Stage stage) throws Exception {
        ApiLibConfig.getInstance().setApiUrl(new URL(AppState.getInstance().getApiUrl()));

        // Ask for login parameters
        Optional<User> userOpt = (new LoginDialog().showAndWait());

        // If the user was provided, load whatever you need
        userOpt.ifPresent(user -> {
            user.login();
            // TODO: Check if login succeeded
            AppState.getInstance().setUser(user);
            startBackgroundDaemon();
            startGui(stage);
        });
    }

    /**
     * Runs background tasks for our app.
     */
    private void startBackgroundDaemon() {
        BackgroundDaemon backgroundDaemon = new BackgroundDaemon();
        backgroundDaemon.start();
    }

    /**
     * Starts the main window.
     *
     * @param stage the primary stage of the app
     */
    private void startGui(final Stage stage) {
        try {
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("/gui/main_window.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException ioe) {
            System.err.println("Error loading the GUI.");
            ioe.printStackTrace();
        }
    }
}
