package com.alphatrader.javagui.gui;

import com.alphatrader.javagui.AppState;
import com.alphatrader.javagui.data.Company;
import com.alphatrader.javagui.data.Notification;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class HashmapDisplayController {
    /**
     * The listview to display the hashmap values.
     */
    @FXML
    private ListView<Map.Entry> hashmapListView;

    /**
     * This function is called by JavaFX after all fxml fields are accessible.
     */
    @FXML
    public void initialize() {
        this.hashmapListView.setCellFactory(e -> new ListCell<Map.Entry>() {
            protected void updateItem(Map.Entry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (entry != null) {
                    setText(entry.getKey().toString() + ": " + String.format("%.02f", entry.getValue()));
                }
            }
        });

        refresh();
    }

    @FXML
    private void refresh() {
        this.hashmapListView.itemsProperty().set(FXCollections.observableList(
            new ArrayList<>(AppState.getInstance().getValuationMap().entrySet()))
        );
    }
}
