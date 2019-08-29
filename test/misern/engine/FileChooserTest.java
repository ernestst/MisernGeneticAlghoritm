package misern.engine;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FileChooserTest {
    @Test
    void testFile() {
        File file = new File("poka.txt");
        Scanner in = FileChooser.createScanner(file);
        String result = FileChooser.loadPopFromFile(in);

        assertEquals("1,13,4,19,12,11", result);
    }
}