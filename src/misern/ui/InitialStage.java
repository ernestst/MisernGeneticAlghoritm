package misern.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import misern.engine.Algorithm;
import misern.engine.FileChooser;

import java.io.File;
import java.util.Scanner;

/**
 * Main frame of the application
 * @author Sebastian Smoliński
 * @author Ernest Stachelski
 * @author Michał Fiłończuk
 * @version 1.2
 */
public class InitialStage extends Application {

    private Stage window;
    private Button populationButton, stepButton, fileButton;
    private ToggleGroup typeGroup, codingGroup;
    private GridPane grid;
    private Algorithm algorithm;
    private File file;

    private TextField pcInput, pmInput, bsInput;

    /**
     * Creates initial application's window
     * @param primaryStage main window
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        grid = new GridPane();
        algorithm = new Algorithm();

        buildWindow();
    }

    /**
     * Starting point of the application
     * @param args unused
     */
    public static void main(String [] args) {
        launch(args);
    }

    private void buildWindow() {
        window.setTitle("Misern Genetic");

        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setHgap(10);

        addPcElements();
        addPmElements();
        addBsElements();

        addCrossoverTypeButtonGroup();
        addCodingTypeButtonGroup();
        addPopulationFileButton();
        addPopulationListButton();
        addNextStepButton();

        window.setScene(new Scene(grid, 680, 230));
        window.show();
    }

    private void addPcElements() {
        Label pcLabel = new Label("Crossover probability: ");
        pcLabel.setAlignment(Pos.CENTER);
        GridPane.setConstraints(pcLabel, 1,0);

        pcInput = new TextField("0.8");
        GridPane.setConstraints(pcInput,2,0);

        Label pcWarning = new Label("(probability with dot 0.0 - 1.0)");
        pcWarning.setAlignment(Pos.CENTER);
        GridPane.setConstraints(pcWarning, 3,0);

        grid.getChildren().addAll(pcLabel, pcInput, pcWarning);
    }

    private void addPmElements() {
        Label pmLabel = new Label("Mutation probability: ");
        pmLabel.setAlignment(Pos.CENTER);
        GridPane.setConstraints(pmLabel, 1,1);

        pmInput = new TextField("0.2");
        GridPane.setConstraints(pmInput,2,1);

        Label pmWarning = new Label("(probability with dot 0.0 - 1.0)");
        pmWarning.setAlignment(Pos.CENTER);
        GridPane.setConstraints(pmWarning, 3,1);

        grid.getChildren().addAll(pmLabel, pmInput, pmWarning);
    }

    private void addBsElements() {
        Label bsLabel = new Label("Best individuals save: ");
        bsLabel.setAlignment(Pos.CENTER);
        GridPane.setConstraints(bsLabel, 1,2);

        bsInput = new TextField("0.3");
        GridPane.setConstraints(bsInput,2,2);

        Label bsWarning = new Label("(part of best individuals to save 0.0 - 1.0)");
        bsWarning.setAlignment(Pos.CENTER);
        GridPane.setConstraints(bsWarning, 3,2);

        grid.getChildren().addAll(bsLabel, bsInput, bsWarning);
    }

    private void addCrossoverTypeButtonGroup() {
        typeGroup = new ToggleGroup();

        Label crossoverTypeLabel = new Label("Crossover type: ");
        crossoverTypeLabel.setAlignment(Pos.CENTER);
        GridPane.setConstraints(crossoverTypeLabel, 1, 4);

        RadioButton onePointButton = new RadioButton("One point");
        onePointButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(onePointButton, 2, 4);

        RadioButton twoPointButton = new RadioButton("Two points");
        twoPointButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(twoPointButton, 3, 4);

        onePointButton.setToggleGroup(typeGroup);
        onePointButton.setSelected(true);
        twoPointButton.setToggleGroup(typeGroup);

        grid.getChildren().addAll(crossoverTypeLabel, onePointButton, twoPointButton);
    }

    private void addCodingTypeButtonGroup() {
        codingGroup = new ToggleGroup();

        Label codingTypeLabel = new Label("Coding type: ");
        codingTypeLabel.setAlignment(Pos.CENTER);
        GridPane.setConstraints(codingTypeLabel, 1, 5);

        RadioButton binaryTypeButton = new RadioButton("Binary");
        binaryTypeButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(binaryTypeButton, 2, 5);

        RadioButton grayTypeButton = new RadioButton("Gray");
        grayTypeButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(grayTypeButton, 3, 5);

        binaryTypeButton.setToggleGroup(codingGroup);
        binaryTypeButton.setSelected(true);
        grayTypeButton.setToggleGroup(codingGroup);

        grid.getChildren().addAll(codingTypeLabel, binaryTypeButton, grayTypeButton);
    }

    private void addPopulationFileButton() {
        fileButton = new Button("File Population");
        fileButton.setOnAction(e -> file = FileChooser.chooseFile());
        fileButton.setAlignment(Pos.CENTER);

        GridPane.setConstraints(fileButton, 1,6);
        GridPane.setFillWidth(fileButton, true);

        grid.getChildren().addAll(fileButton);
    }

    private void addPopulationListButton() {
        populationButton = new Button("Show population");
        populationButton.setAlignment(Pos.CENTER);

        GridPane.setConstraints(populationButton, 2,6);
        GridPane.setFillWidth(populationButton, true);

        populationButton.setOnAction(e -> populationListHandler());
        grid.getChildren().addAll(populationButton);
    }

    private void populationListHandler() {
        if(file != null) {
            Scanner in = FileChooser.createScanner(file);
            String population = FileChooser.loadPopFromFile(in);

            if(algorithm.getPop().size() == 0 || algorithm.getPop().size() != population.split(",").length) {
                algorithm.setInitialPopulation(population);
            }

            if(((ToggleButton)codingGroup.getSelectedToggle()).getText().equals("Binary"))
                PopulationBox.display(algorithm.getPop().printPopulation(Algorithm.CODING_BINARY));
            else
                PopulationBox.display(algorithm.getPop().printPopulation(Algorithm.CODING_GRAY));
        } else {
            ErrorBox.display("You have to choose file with initial population first!");
        }
    }

    private void addNextStepButton() {
        stepButton = new Button("Simulate generation");
        stepButton.setAlignment(Pos.CENTER);

        GridPane.setConstraints(stepButton, 3,6);
        GridPane.setFillWidth(stepButton, true);

        stepButton.setOnAction(e -> nextStepHandler());
        grid.getChildren().addAll(stepButton);
    }

    private void nextStepHandler() {
        if(file != null) {
            Scanner in = FileChooser.createScanner(file);
            String population = FileChooser.loadPopFromFile(in);

            if(algorithm.getPop().size() == 0) {
                algorithm.setInitialPopulation(population);
            }

            pcInput.setDisable(true);
            pmInput.setDisable(true);
            bsInput.setDisable(true);
            fileButton.setDisable(true);
            typeGroup.getToggles().forEach(t -> ((Node)t).setDisable(true));
            codingGroup.getToggles().forEach(t -> ((Node)t).setDisable(true));

            try {
                algorithm.setPc(Double.parseDouble(pcInput.getText()));
                algorithm.setPm(Double.parseDouble(pmInput.getText()));
                algorithm.setBs(Double.parseDouble(bsInput.getText()));
            } catch(NumberFormatException ex) {
                ErrorBox.display("Check if every input is filled with correct data.");
            }

            int type;
            if(((ToggleButton)codingGroup.getSelectedToggle()).getText().equals("Binary"))
                type = Algorithm.CODING_BINARY;
            else
                type = Algorithm.CODING_GRAY;

            if(((ToggleButton)typeGroup.getSelectedToggle()).getText().equals("One point")) {
                algorithm.nextStep(Algorithm.CROSSING_ONE_POINT, type);
            } else {
                algorithm.nextStep(Algorithm.CROSSING_TWO_POINTS, type);
            }

        }
    }
}
