package misern.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import misern.engine.Pause;

import java.util.concurrent.TimeUnit;

/**
 * Information box with information about results of individual's chromosome mutation
 * @author Sebastian Smoliński
 * @author Michał Fiłońćzuk
 * @author Ernest Stachelski
 * @version 1.1
 */
public class MutationBox {
    /**
     * Displays box with mutation's results
     * @param before binary/gray form before the mutation
     * @param after binary/gray form after the mutation
     * @param point point of the mutation to bold it
     */
    public static void display(String before, String after, int point) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Mutation");
        window.setMinWidth(250);

        Pause.pauseProgram(2, window);

        Button closeButton = new Button("Next");
        closeButton.setOnAction(e -> window.close());

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException ignored) {}

        TextFlow beforeFlow = generateTextFlow(before, point, "Before: ");
        TextFlow afterFlow = generateTextFlow(after, point, "After:   ");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(beforeFlow, afterFlow, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static TextFlow generateTextFlow(String raw, int position, String title) {
        TextFlow flow = new TextFlow();

        position = raw.length() - position - 1;

        Text text1 = new Text(raw.substring(0, position));
        text1.setStyle("-fx-font-weight: normal;");

        Text text2 = new Text(raw.charAt(position)+"");
        text2.setStyle("-fx-font-weight: bold;");

        if(position != raw.length()-1) {
            Text text3 = new Text(raw.substring(position + 1));
            text3.setStyle("-fx-font-weight: normal;");
            flow.getChildren().addAll(new Text(title), text1, text2, text3);
        } else {
            flow.getChildren().addAll(new Text(title), text1, text2);
        }

        flow.setTextAlignment(TextAlignment.CENTER);
        return flow;
    }
}
