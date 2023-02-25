package rainyday;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domainclasses.Task;
import parser.FileManager;

public class TestTaskManager {
    
    private final String FILEPATH = "src/test/resources/input/Eggs.tsv";
    private final String DELIMITER = "\t";

    @Test
    public void testReadFile() {
        // Test for a file that does not exist
        FileManager fm = new FileManager("src/test/resources/input/DoesNotExist.tsv", "\t");
        List<Task> tasks = new ArrayList<>();
        tasks = fm.giveTasks();
        assertEquals(0, tasks.size());
        assertNotNull(tasks);

        // Test for the wrong delimiter
        fm = new FileManager("src/test/resources/input/Eggs.tsv", ",");
        tasks = fm.giveTasks();
        assertEquals(0, tasks.size());
        assertNotNull(tasks);
    }

    
}
