package com.alphatrader.javagui.gui;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Company;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

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
     * Displays the company name in the center screen.
     */
    @FXML
    private Label companyNameLabel;

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
     * @param company the company to manage in the center screen
     */
    private void displayCompany(Company company) {
        companyNameLabel.setText(company.getName());
    }
}
