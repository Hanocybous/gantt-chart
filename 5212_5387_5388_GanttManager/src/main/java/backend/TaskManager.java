package backend;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dom2app.SimpleTableModel;
import domainclasses.*;
import parser.FileManager;
import reporter.IReporter;
import reporter.ReporterFactory;

class TaskManager implements IMainController {

	private static final String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
	private static TaskManager instance = null;

	private ArrayList<Task> tasks;
	private FileManager taskCreator;
	
	private TaskManager() { }
	
	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}

	public SimpleTableModel load(String fileName, String delimiter) {
		
		taskCreator = new FileManager(fileName,delimiter);
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

}