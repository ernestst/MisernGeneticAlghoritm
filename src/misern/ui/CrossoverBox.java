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
import misern.encrypt.Encrypt;
import misern.engine.Individual;
import misern.engine.Pause;

import java.util.concurrent.TimeUnit;

/**
 * Crossover box with information about result of the crossing individuals
 * @author Ernest Stachelski
 * @author Sebastian Smoliński
 * @author Michał Fiłończuk
 * @version 1.2
 */
public class CrossoverBox {
    /**
     * Display window with result of the crossing using one point crossing type
     * @param father individual showing as father (up)
     * @param mother individual showing as mother (bottom)
     * @param child individual showing as child (without bold)
     * @param bits number of bits should every binary form has
     */
    public static void displayOnePoint(Individual father, Individual mother, Individual child, int bits) {
        Stage window = createWindow();

        Button closeButton = new Button("Next");
        closeButton.setOnAction(e -> window.close());

        int point = bits / 2;
        String fatherString = Encrypt.toBinary(father.getNumber(), bits);
        String motherString = Encrypt.toBinary(mother.getNumber(), bits);
        String childString = Encrypt.toBinary(child.getNumber(), bits);

        TextFlow fatherFlow = generateTextFlow("Father: ", 1, point, fatherString);
        TextFlow motherFlow = generateTextFlow("Mother: ", 2, point, motherString);
        TextFlow childFlow = generateTextFlow("Child: ", 0, point, childString);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(fatherFlow, motherFlow, childFlow, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Display window with result of the crossing using two points crossing type
     * @param father individual showing as father (up)
     * @param mother individual showing as mother (bottom)
     * @param child individual showing as child (without bold)
     * @param bits number of bits should every binary form has
     */
    public static void displayTwoPoints(Individual father, Individual mother, Individual child, int bits) {
        Stage window = createWindow();

        Button closeButton = new Button("Next");
        closeButton.setOnAction(e -> window.close());

        int point = (int)(bits * 0.33);
        int point2 = (int)(bits * 0.66);

        String fatherString = Encrypt.toBinary(father.getNumber(), bits);
        String motherString = Encrypt.toBinary(mother.getNumber(), bits);
        String childString = Encrypt.toBinary(child.getNumber(), bits);

        TextFlow fatherFlow = generateTwopointTextFlow("Father: ", 1, point, point2, fatherString);
        TextFlow motherFlow = generateTwopointTextFlow("Mother: ", 2, point, point2, motherString);
        TextFlow childFlow = generateTextFlow("Child: ", 0, point, childString);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(fatherFlow, motherFlow, childFlow, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static Stage createWindow() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Crossover Result");
        window.setMinWidth(250);

        Pause.pauseProgram(2, window);

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException ignored) {}

        return window;
    }

    private static TextFlow generateTextFlow(String person, int part, int point, String raw) {
        TextFlow flow = new TextFlow();

        Text text1 = new Text(raw.substring(0, point));
        if(part == 1) {
            text1.setStyle("-fx-font-weight: bold;");
        } else if(part == 2 || part == 0) {
            text1.setStyle("-fx-font-weight: normal;");
        }

        Text text2 = new Text(raw.substring(point));
        if(part == 1 || part == 0) {
            text2.setStyle("-fx-font-weight: normal;");
        } else if(part == 2) {
            text2.setStyle("-fx-font-weight: bold;");
        }

        flow.getChildren().addAll(new Text(person), text1, text2);
        flow.setTextAlignment(TextAlignment.CENTER);

        return flow;
    }

    private static TextFlow generateTwopointTextFlow(String person, int part, int point1, int point2, String raw) {
        TextFlow flow = new TextFlow();

        Text text1 = new Text(raw.substring(0, point1+1));
        if(part == 1) {
            text1.setStyle("-fx-font-weight: bold;");
        } else if(part == 2 || part == 0) {
            text1.setStyle("-fx-font-weight: normal;");
        }

        Text text2 = new Text(raw.substring(point1+1, point2));
        if(part == 1 || part == 0) {
            text2.setStyle("-fx-font-weight: normal;");
        } else if(part == 2) {
            text2.setStyle("-fx-font-weight: bold;");
        }

        Text text3 = new Text(raw.substring(point2));
        if(part == 1) {
            text3.setStyle("-fx-font-weight: bold;");
        } else if(part == 2 || part == 0) {
            text3.setStyle("-fx-font-weight: normal;");
        }

        flow.getChildren().addAll(new Text(person), text1, text2, text3);
        flow.setTextAlignment(TextAlignment.CENTER);

        return flow;
    }
}
