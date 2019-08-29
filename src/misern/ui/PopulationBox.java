package misern.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Box with population's list
 * @author Sebastian Smoliński
 * @author Michał Fiłońćzuk
 * @author Ernest Stachelski
 * @version 1.0
 */
class PopulationBox {

    /**
     * Displays window with whole list of the actual population
     * @param individualsList list with all individuals in gray or binary form
     */
    static void display(String [] individualsList) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Actual population");
        window.setMinWidth(250);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        ObservableList<String> names = FXCollections.observableArrayList(individualsList);
        ListView<String> populationListView = new ListView<>(names);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(populationListView, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
