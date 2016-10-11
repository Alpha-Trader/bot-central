package com.alphatrader.javagui.gui;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Company;
import com.alphatrader.javagui.data.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.text.DecimalFormat;

/**
 * This controller handles all requests coming from the gui to our backend.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class MainWindowController {
    /**
     * The list view displaying all the companies the current user is the CEO of.
     */
    @FXML
    private ListView<Company> companyListView;

    /**
     * The list view displaying all notifications for the logged-in user.
     */
    @FXML
    private ListView<Notification> notificationListView;

    /**
     * Displays the company name in the center screen.
     */
    @FXML
    private Label companyNameLabel;

    /**
     * Displays the company's cash reserves.
     */
    @FXML
    private Label cashLabel;

    /**
     * This function is called by JavaFX after all fxml fields are accessible.
     */
    @FXML
    public void initialize() {
        this.companyListView.setCellFactory(e -> new ListCell<Company>() {
            protected void updateItem(Company company, boolean empty) {
                super.updateItem(company, empty);
                if (company != null) {
                    setText(company.getName());
                }
            }
        });
        this.companyListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> displayCompany(newVal)
        );

        this.notificationListView.setCellFactory(e -> new ListCell<Notification>() {
            protected void updateItem(Notification notification, boolean empty) {
                super.updateItem(notification, empty);
                if (notification != null) {
                    setText(notification.getMessage());
                }
            }
        });
        this.notificationListView.itemsProperty().set(AppState.getInstance().getNotifications());

        refresh();

        this.companyListView.getSelectionModel().select(0);
    }

    /**
     * Reload the company list from the server.
     */
    @FXML
    private void refresh() {
        this.companyListView.itemsProperty().setValue(
            FXCollections.observableList(
                Company.getAllUserCompanies(AppState.getInstance().getUser())
            )
        );
    }

    /**
     * Exits the application by first exiting the JavaFX platform, then the JVM.
     */
    @FXML
    private void exitApp() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Displays the selected company in the center screen.
     *
     * @param company the company to manage in the center screen
     */
    private void displayCompany(Company company) {
        companyNameLabel.setText(company.getName() + " - " + company.getSecurityIdentifier());
        cashLabel.setText("Cash: " + String.format("%.02f", company.getCash()));
    }
}
