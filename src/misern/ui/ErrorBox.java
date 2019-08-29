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

/**
 * Error box displaying information about some wrong behavior
 * @author Sebastian Smoliński
 * @author Ernest Stachelski
 * @author Michał Fiłończuk
 * @version 1.0
 */
public class ErrorBox {
    /**
     * Displays popup with given message and wait for close
     * @param message text to render inside window
     */
    public static void display(String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error!");
        window.setMinWidth(250);

        Pause.pauseProgram(2, window);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(new Text(message), closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
