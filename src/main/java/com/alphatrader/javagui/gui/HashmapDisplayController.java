package com.alphatrader.javagui.gui;

import com.alphatrader.javagui.estimation.Estimator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
        Map<String, Double> prices = Estimator.get(Estimator.EstimatorType.FUNDAMENTAL)
            .getSecurityEstimations();
        SortedMap<String, Double> sortedPrices = new TreeMap<>(prices);
        this.hashmapListView.itemsProperty().set(FXCollections.observableList(
            new ArrayList<>(sortedPrices.entrySet())
        ));
    }
}
