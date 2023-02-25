package rainyday;

import static org.junit.Assert.*;

import org.junit.Test;

import domainclasses.SimpleTask;
import domainclasses.Task;

public class TestTask {
    
    //Test the rainday scenario for the method "getTaskName"
    @Test
    public void testGetTaskName() {
        Task task = new SimpleTask(10, null, 0, 50, 70, 70.0);
        assertNull(task.getName());
    }

    //Test the rainday scenario for the method "getTaskStart"
    @Test
    public void testGetTaskStart() {
        Task task = new SimpleTask(10, "Task 1", 0, 90, 70, 70.0);
        // If start is greater than end, then throw an exception
        if (task.getStart() > task.getEnd()) {
            //throw new IllegalArgumentException("Start time cannot be greater than end time");
        }
        assertEquals(90, task.getStart());
    }
    
    //Test the rainday scenario for the method "getTaskCost"
    @Test
    public void testGetTaskCost() {
        // If cost is less than 0, then throw an exception
        Task task = new SimpleTask(10, "Task 1", 0, 50, 70, -70.0);
        if (task.getCost() < 0) {
            //throw new IllegalArgumentException("Cost cannot be less than 0");
        }
        assertEquals(-70.0, task.getCost(), 0.0);
    }

    //Test the rainday scenario for the method "getTaskId"
    @Test
    public void testGetTaskId() {
        Task task = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
        assertEquals(10, task.getId());
    }

    //Test the rainday scenario for the method getName for an array of tasks
    @Test
    public void testGetTaskNameArray() {
        Task[] tasks = new Task[3];
        tasks[0] = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
        tasks[1] = new SimpleTask(11, "Task 2", 0, 50, 70, 70.0);
        tasks[2] = new SimpleTask(12, "Task 3", 0, 50, 70, 70.0);
        assertEquals("Task 1", tasks[0].getName());
        assertEquals("Task 2", tasks[1].getName());
        assertEquals("Task 3", tasks[2].getName());
    }
    
    //Test the rainday scenario for the method getStart for an array of tasks
    @Test
    public void testGetTaskStartArray() {
        Task[] tasks = new Task[3];
        tasks[0] = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
        tasks[1] = new SimpleTask(11, "Task 2", 0, 50, 70, 70.0);
        tasks[2] = new SimpleTask(12, "Task 3", 0, 50, 70, 70.0);
        assertEquals(50, tasks[0].getStart());
        assertEquals(50, tasks[1].getStart());
        assertEquals(50, tasks[2].getStart());
    }

    //Test the rainday scenario for the method getCost for an array of tasks
    @Test
    public void testGetTaskCostArray() {
        Task[] tasks = new Task[3];
        tasks[0] = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
        tasks[1] = new SimpleTask(11, "Task 2", 0, 50, 70, 70.0);
        tasks[2] = new SimpleTask(12, "Task 3", 0, 50, 70, 70.0);
        assertEquals(70.0, tasks[0].getCost(), 0.0);
        assertEquals(70.0, tasks[1].getCost(), 0.0);
        assertEquals(70.0, tasks[2].getCost(), 0.0);
    }

    //Test the rainday scenario for the method getId for an array of tasks
    @Test
    public void testGetTaskIdArray() {
        Task[] tasks = new Task[3];
        tasks[0] = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
        tasks[1] = new SimpleTask(11, "Task 2", 0, 50, 70, 70.0);
        tasks[2] = new SimpleTask(12, "Task 3", 0, 50, 70, 70.0);
        assertEquals(10, tasks[0].getId());
        assertEquals(11, tasks[1].getId());
        assertEquals(12, tasks[2].getId());
    }

}
