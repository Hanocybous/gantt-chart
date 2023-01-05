package rainyday;

import static org.junit.Assert.*;

import org.junit.Test;

import dom2app.SimpleTableModel;

public class TestSimpleTableModel {

    @Test
    public void testSimpleTableModel() {
        // Test the occassional null pointer exception
        SimpleTableModel stm = new SimpleTableModel("Eggs", "Eggs", null, null);
        // Assert that the name and project name are set
        assertEquals("Eggs", stm.getName());
        assertEquals("Eggs", stm.getPrjName());
        // Assert that the column names are null
        try {
            stm.getColumnName(0);
        } catch (NullPointerException e) {
            // This is expected
        }
        try {
            stm.getValueAt(0, 0);
        } catch (NullPointerException e) {
            // This is expected
        }

    }
    
}
