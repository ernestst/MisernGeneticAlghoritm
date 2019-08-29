package misern.engine;

import javafx.stage.Stage;
import misern.ui.ErrorBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Simple chooser for txt files that include initial populations
 * @author Ernest Stachelski
 * @version 1.0
 */
public class FileChooser {

    /**
     * Shows file chooser and handles file
     * @return chosen txt file
     */
    public static File chooseFile() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("TXT", "*.txt")
        );

        Stage stage = new Stage();

        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Creates scanner for chosen file
     * @param file file that was chosen in file chooser
     * @return opened scanner
     */
    public static Scanner createScanner(File file) {
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            ErrorBox.display("File doesn't exist (anymore).");
        }

        return in;
    }

    /**
     * Creates raw type string with individuals separated by commas
     * @param in opened input file
     * @return string with number of individuals separated by commas
     */
    public static String loadPopFromFile(Scanner in) {
        StringBuilder population = new StringBuilder();

        if (in != null) {
            while(in.hasNextLine()) {
                population.append(in.nextLine());
                population.append(",");
            }
        } else {
            ErrorBox.display("File doesn't contain valid structure.");
        }

        population.deleteCharAt(population.length()-1);
        return population.toString();
    }
}
