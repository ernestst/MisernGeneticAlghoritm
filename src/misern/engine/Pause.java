package misern.engine;

import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Tools for pausing popups in application.
 * @author Michał Fiłończuk
 * @version 1.0
 */
public class Pause {
    /**
     * Pauses whole program for given portion of time
     * @param time time portion in seconds
     * @param window Stage that should be paused
     */
    public static void pauseProgram(int time, Stage window) {
        PauseTransition wait = new PauseTransition(Duration.seconds(time));

        wait.setOnFinished((e) -> {
            window.close();
            wait.playFromStart();
        });

        wait.play();
    }
}
