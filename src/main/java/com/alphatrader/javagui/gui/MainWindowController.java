package com.alphatrader.javagui.gui;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.estimation.Estimator;
import com.alphatrader.rest.Company;
import com.alphatrader.rest.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
     * Displays the company's estimated stock value.
     */
    @FXML
    private Label stockValueLabel;

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
                    Text text = new Text(notification.getMessage().getFilledString());
                    text.setWrappingWidth(e.getPrefWidth() * 0.9);
                    setGraphic(text);
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

    @FXML
    private void displayCompanyValuations() {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/gui/hashmap_display.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Company Valuation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ioe) {
            System.err.println("Error loading the GUI.");
            ioe.printStackTrace();
        }
    }

    /**
     * Displays the selected company in the center screen.
     *
     * @param company the company to manage in the center screen
     */
    private void displayCompany(Company company) {
        if(company != null) {
            companyNameLabel.setText(company.getName());
            cashLabel.setText("Cash: " + String.format("%.02f", Double.NaN));
            stockValueLabel.setText("est. Stock Value: "
                + String.format("%.02f", Estimator.get(Estimator.EstimatorType.FUNDAMENTAL).evaluate(company)));
        }
    }
}
