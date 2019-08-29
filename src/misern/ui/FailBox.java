package misern.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import misern.engine.Pause;

import java.util.concurrent.TimeUnit;

/**
 * Fail box with information about failed try of crossing individuals
 * @author Sebastian Smoliński
 * @author Michał Fiłońćzuk
 * @author Ernest Stachelski
 * @version 1.1
 */
public class FailBox {
    /**
     * Displays box and closes it after 2 seconds
     */
    public static void display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Crossing fail");
        window.setMinWidth(250);

        Pause.pauseProgram(2, window);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException ignored) {}

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(new Text("Failed to crossover individuals (probability isn't 100%)"), closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
