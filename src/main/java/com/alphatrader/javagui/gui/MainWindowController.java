package com.alphatrader.javagui.gui;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Company;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * This controller handles all requests coming from the gui to our backend.
 *
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class MainWindowController {
    @FXML
    private ListView<Company> companyListView;

    @FXML
    public void initialize() {


        this.companyListView.setCellFactory(e -> {
            return new ListCell<Company>() {
                protected void updateItem(Company company, boolean empty) {
                    super.updateItem(company, empty);
                    if (company != null) {
                        setText(company.getName());
                    }
                }
            };
        });

        refresh();
    }

    @FXML
    public void refresh() {
        this.companyListView.itemsProperty().setValue(
            FXCollections.observableList(
                Company.getAllUserCompanies(AppState.getInstance().getUser())
            )
        );
    }

    /**
     * Exits the application by first closing
     */
    @FXML
    private void exitApp() {
        Platform.exit();
        System.exit(0);
    }
}
