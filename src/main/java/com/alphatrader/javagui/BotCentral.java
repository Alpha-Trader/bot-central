package com.alphatrader.javagui;

import java.util.Optional;

import com.alphatrader.javagui.data.Bond;
import com.alphatrader.javagui.data.Stock;
import com.alphatrader.javagui.data.User;
import com.alphatrader.javagui.gui.LoginDialog;
import javafx.application.Application;
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
   * @param args commandline parameters
   */
  public static void main(String[] args) {
        launch(args);
    }

  /**
   * This is where the action takes place.
   * @param stage the primary stage for our GUI
   * @throws Exception whenever it feels like it
   */
    @Override
    public void start(final Stage stage) throws Exception {
        // Ask for login parameters
        Optional<User> userOpt = (new LoginDialog().showAndWait());

        // If the login was successful, load whatever you need
        userOpt.ifPresent(user -> {
            user.login();
            AppState.getInstance().setUser(user);
            System.out.println(Bond.getAllBonds());
            System.out.println(Stock.getAllStocks());
        });
    }
}
