package happyday;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domtoapp.SimpleTableModel;

public class TestSimpleTableModel {
    
    @Test
    public void testSimpleTableModel() {
        String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"1","Prepare Fry","0","1","2","1"});
        data.add(new String[]{"2","Turn on burner (low)","1","1","2","1"});
        data.add(new String[]{"3","Break eggs and pour into fry","1","1","2","1"});
        SimpleTableModel stm = new SimpleTableModel("Eggs", "Eggs", columnNames, data);
        assertEquals("Eggs", stm.getName());
        assertEquals("Eggs", stm.getPrjName());
        assertEquals("TaskId", stm.getColumnName(0));
        assertEquals("TaskText", stm.getColumnName(1));
        assertEquals("MamaId", stm.getColumnName(2));
        assertEquals("Start", stm.getColumnName(3));
        assertEquals("End", stm.getColumnName(4));

    }
    
    @Test
    public void testSimpleTableModel2() {
        String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"1","Prepare Fry","0","1","2","1"});
        data.add(new String[]{"2","Turn on burner (low)","1","1","2","1"});
        data.add(new String[]{"3","Break eggs and pour into fry","1","1","2","1"});
        SimpleTableModel stm = new SimpleTableModel("Eggs", "Eggs", columnNames, data);
        assertEquals("Eggs", stm.getName());
        assertEquals("Eggs", stm.getPrjName());
        assertEquals("TaskId", stm.getColumnName(0));
        assertEquals("TaskText", stm.getColumnName(1));
        assertEquals("MamaId", stm.getColumnName(2));
        assertEquals("Start", stm.getColumnName(3));
        assertEquals("End", stm.getColumnName(4));
        assertEquals("Cost", stm.getColumnName(5));
        assertEquals("1", stm.getValueAt(0, 0));
        assertEquals("Prepare Fry", stm.getValueAt(0, 1));
        assertEquals("0", stm.getValueAt(0, 2));
        assertEquals("1", stm.getValueAt(0, 3));
        assertEquals("2", stm.getValueAt(0, 4));
        assertEquals("1", stm.getValueAt(0, 5));
        assertEquals("2", stm.getValueAt(1, 0));
        assertEquals("Turn on burner (low)", stm.getValueAt(1, 1));
        assertEquals("1", stm.getValueAt(1, 2));
        assertEquals("1", stm.getValueAt(1, 3));
        assertEquals("2", stm.getValueAt(1, 4));
        assertEquals("1", stm.getValueAt(1, 5));
        assertEquals("3", stm.getValueAt(2, 0));
        assertEquals("Break eggs and pour into fry", stm.getValueAt(2, 1));
        assertEquals("1", stm.getValueAt(2, 2));
        assertEquals("1", stm.getValueAt(2, 3));
        assertEquals("2", stm.getValueAt(2, 4));
        assertEquals("1", stm.getValueAt(2, 5));
    }
    
}
