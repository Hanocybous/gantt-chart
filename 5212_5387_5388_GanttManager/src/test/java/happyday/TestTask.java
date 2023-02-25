package happyday;

import static org.junit.Assert.*;

import org.junit.Test;

import domainclasses.ComplexTask;
import domainclasses.SimpleTask;
import domainclasses.Task;

public class TestTask {

	// Test for the method "getTaskName"
	@Test
	public void testGetTaskName() {
		Task task = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
		assertEquals("Task 1", task.getName());
	}

	// Test for the method "getTaskStart"
	@Test
	public void testGetTaskStart() {
		Task task = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
		assertEquals(50, task.getStart());
	}

	// Test for the method "getTaskEnd"
	@Test
	public void testGetTaskEnd() {
		Task task = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
		assertEquals(70, task.getEnd());
	}

	// Test for the method "getTaskCost"
	@Test
	public void testGetTaskCost() {
		Task task = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
		assertEquals(70.0, task.getCost(), 0.0);
	}

	// Test for the method "getTaskId"
	@Test
	public void testGetTaskId() {
		Task task = new SimpleTask(10, "Task 1", 0, 50, 70, 70.0);
		assertEquals(10, task.getId());
	}

	// Test the method getName for the class ComplexTask
	@Test
	public void testComplexTaskGetName() {
		Task task = new ComplexTask(10, "Task 1", 0);
		assertEquals("Task 1", task.getName());
	}

	// Test the method getStart for the class ComplexTask
	@Test
	public void testComplexTaskGetStart() {
		Task task = new ComplexTask(10, "Task 1", 0);
		assertEquals(-1, task.getStart());
	}

	// Test the method getEnd for the class ComplexTask with subtasks
	@Test
	public void testComplexTaskGetEndWithSubtasks() {
		Task task = new ComplexTask(10, "Task 1", 0);
		task.addSubTask(new SimpleTask(11, "Task 2", 0, 0, 10, 10.0));
		task.addSubTask(new SimpleTask(12, "Task 3", 0, 10, 20, 20.0));
		assertEquals(20, task.getEnd());
	}

	// Test the method getEnd for the class ComplexTask without subtasks
	@Test
	public void testComplexTaskGetEndWithoutSubtasks() {
		Task task = new ComplexTask(10, "Task 1", 0);
		assertEquals(-1, task.getEnd());
	}

	// Test the method getCost for the class ComplexTask with subtasks
	@Test
	public void testComplexTaskGetCostWithSubtasks() {
		Task task = new ComplexTask(10, "Task 1", 0);
		task.addSubTask(new SimpleTask(11, "Task 2", 0, 0, 10, 10.0));
		task.addSubTask(new SimpleTask(12, "Task 3", 0, 10, 20, 20.0));
		assertEquals(30.0, task.getCost(), 0.0);
	}

	// Test the method getCost for the class ComplexTask without subtasks
	@Test
	public void testComplexTaskGetCostWithoutSubtasks() {
		Task task = new ComplexTask(10, "Task 1", 0);
		assertEquals(0.0, task.getCost(), 0.0);
	}

	// Test the method getId for the class ComplexTask
	@Test
	public void testComplexTaskGetId() {
		Task task = new ComplexTask(10, "Task 1", 0);
		assertEquals(10, task.getId());
	}

}
