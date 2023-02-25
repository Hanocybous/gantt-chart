package backend;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import domainclasses.*;
import domtoapp.SimpleTableModel;
import parser.FileManager;
import reporter.IReporter;
import reporter.ReporterFactory;

class TaskManager implements IMainController {

	private static final String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
	private static TaskManager instance = null;

	private ArrayList<Task> tasks;	
	
	private TaskManager() { }
	
	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}

	public SimpleTableModel load(String fileName, String delimiter) {
		
		FileManager taskCreator = new FileManager(fileName,delimiter);
		tasks = (ArrayList<Task>) taskCreator.giveTasks();
		
		Comparator<Task> comparator = (o1, o2) -> {
		    // Compare the two objects based on their id, if they have the same id, compare their start
		    if(o1.getMamaId() !=0 && o2.getMamaId() != 0 && o1.getMamaId() == o2.getMamaId() && o1.getStart() != o2.getStart()) {
		        return o1.getStart() - o2.getStart();
		    }else if(o1.getMamaId() !=0 && o2.getMamaId() != 0 && o1.getMamaId() == o2.getMamaId() && o1.getStart() == o2.getStart()) {
		    	return o1.getEnd() - o2.getEnd();
		    }
		    return o1.getId() - o2.getId();
		};

        // Sort the tasks by id and start
        tasks.sort(comparator);

        List<String[]> tasksToReturn = new ArrayList<>();
        for(Task task : tasks) {
        	tasksToReturn.add(task.stringTask());
        }

        // Return the SimpleTableModel
        return new SimpleTableModel("Comp", "Comp", columnNames, tasksToReturn);
	}
	
	public SimpleTableModel getTasksByPrefix(String prefix) {
		List<String[]> prefixData = new ArrayList<>();
		for(Task checkingTask : tasks) {
			if (checkingTask.getName().startsWith(prefix)) {
				prefixData.add(checkingTask.stringTask());
			}
		}
		return new SimpleTableModel("Prefix","Prefix", columnNames,prefixData);
	}
	
	public SimpleTableModel getTaskById(int id) {
		List<String[]> idData = new ArrayList<>();
		for (Task idTask : tasks) {
			if (idTask.getId() == id) {   
				idData.add(idTask.stringTask());
				return new SimpleTableModel("Id","Id", columnNames,idData);
			}
		}
		return null; // maybe put a blank table model
	}
	
	public SimpleTableModel getTopLevelTasks() {
		List<String[]> topLevelData = new ArrayList<>();
		for (Task topLevelTask : tasks) {
			if (topLevelTask.getMamaId() == 0) {
				topLevelData.add(topLevelTask.stringTask());
			}
		}
		return new SimpleTableModel("TopLevel","TopLevel", columnNames,topLevelData);
	}
	
	public int createReport(String path, ReportType type) {
		IReporter reporter = ReporterFactory.getReporter(path, tasks, type);
		return reporter.createReport();
	}

	public void deleteTask(int selectedId) {
		for (Task task : tasks) {
			if (task.getId() == selectedId) {
				tasks.remove(task);
				break;
			}
		}
	}

	public void updateTask(int selectedId, int choice, String newValue) {
		for (Task task : tasks) {
			if (task.getId() == selectedId) {
				switch (choice) {
				case 0:
					task.setTaskId(Integer.parseInt(newValue));
					break;
				case 1:
					task.setTaskDescription(newValue);
					break;
				case 2:
					task.setMamaId(Integer.parseInt(newValue));
					break;
				case 3:
					task.setStart(Integer.parseInt(newValue));
					break;
				case 4:
					task.setEnd(Integer.parseInt(newValue));
					break;
				case 5:
					task.setCost((double) Integer.parseInt(newValue));
					break;
				}
				break;
			}
		}
	}

	public SimpleTableModel getTaskList() {
		List<String[]> taskList = new ArrayList<>();
		for (Task task : tasks) {
			taskList.add(task.stringTask());
		}
		return new SimpleTableModel("TaskList","TaskList", columnNames,taskList);
	}

	public void addTask(String taskText, int mamaId, String startDateString, String endDateString, double cost) {
		int id = tasks.get(tasks.size()-1).getId() + 1;
		int start = Integer.parseInt(startDateString);
		int end = Integer.parseInt(endDateString);
		Task newTask = new SimpleTask(id, taskText, mamaId, start, end, cost);
		tasks.add(newTask);
	}

	public void addTask(String taskText, int mamaId, String startDateString, String endDateString, double cost, int taskId) {
		int start = Integer.parseInt(startDateString);
		int end = Integer.parseInt(endDateString);
		Task newTask = new SimpleTask(taskId, taskText, mamaId, start, end, cost);
		tasks.add(newTask);
	}

	@Override
	public String[] getTaskData(int selectedId) {
		String[] taskData = new String[5];
		for (Task task : tasks) {
			if (task.getId() == selectedId) {  
				taskData[0] = task.getName();
				taskData[1] = Integer.toString(task.getStart());
				taskData[2] = Integer.toString(task.getEnd());
				taskData[3] = Double.toString(task.getCost());
				taskData[4] = Integer.toString(task.getId());
				return taskData;
			}
			// if the selected id is a mama task, return the data of it
			if (task.getId() == selectedId && task.getMamaId() == 0) {
				return new String[0];
			}
		}
		return new String[0];
	}

	@Override
	public void updateMamaTask(int mamaId, String mamaStartDateString, String mamaEndDateString, Double mamaCost) {
		int mamaStartDate = Integer.parseInt(mamaStartDateString);
		int mamaEndDate = Integer.parseInt(mamaEndDateString);
		for (Task task : tasks) {
			if (task.getId() == mamaId) {
				task.setStart(mamaStartDate);
				task.setEnd(mamaEndDate);
				task.setCost(mamaCost);
				break;
			}
		}
	}

	@Override
	public void deleteSubTasks(int selectedId) {
		List<Task> subTasks = new ArrayList<>();
		for (Task task : tasks) {
			if (task.getMamaId() == selectedId) {
				subTasks.add(task);
			}
		}
		for (Task subTask : subTasks) {
			tasks.remove(subTask);
		}
	}


}