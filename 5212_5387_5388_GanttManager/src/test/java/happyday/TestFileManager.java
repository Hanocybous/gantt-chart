package happyday;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domainclasses.Task;
import parser.FileManager;

public class TestFileManager {
    
    @Test
    public void testReadFile() {
        FileManager fm = new FileManager("src/test/resources/input/Eggs.tsv", "\t");
        List<Task> tasks = new ArrayList<>();
        tasks = fm.giveTasks();
        assertNotNull(tasks);
        assertEquals("Prepare Fry", tasks.get(0).getName());
    }

}
