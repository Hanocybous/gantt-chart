package rainyday;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import domainClasses.Task;
import fileManager.FileManager;

public class TestFileManager {
    
    @Test
    public void testReadFile() {
        // Test for a file that does not exist
        FileManager fm = new FileManager("src/test/resources/input/DoesNotExist.tsv", "\t");
        ArrayList<Task> tasks = new ArrayList<>();
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
