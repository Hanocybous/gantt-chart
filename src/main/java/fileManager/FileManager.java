package fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import domainClasses.*;

public class FileManager {
	String path;
	String delimiter;
	ArrayList<Task> returnTaskList;
	
	public FileManager(String path, String delimiter) {
		this.path = path;
		this.delimiter = delimiter;
		returnTaskList = new ArrayList<>();
	}	
	
	public List<Task> giveTasks(){
		try {
	      File file = new File(path);
	      Scanner myReader = new Scanner(file);
	      while (myReader.hasNextLine()) {
	    	  ArrayList<String> taskData = new ArrayList<>();
	    	  String[] line = myReader.nextLine().split(delimiter);
	    	  Task tempTask;
	    	  taskData = new ArrayList<>(Arrays.asList(line));

	    	  if (taskData.size() == 6) {
	    		  tempTask = new SimpleTask(Integer.parseInt(taskData.get(0)),
	    				  taskData.get(1),
	    				  Integer.parseInt(taskData.get(2)),
	    				  Integer.parseInt(taskData.get(3)),
	    				  Integer.parseInt(taskData.get(4)),
	    				  Double.parseDouble(taskData.get(5)));
	    		  returnTaskList.add(tempTask);
	    	  }else if (taskData.size() == 3) {
	    		  tempTask = new ComplexTask(Integer.parseInt(taskData.get(0)),
	    				  taskData.get(1),
	    				  Integer.parseInt(taskData.get(2)));
	    		  returnTaskList.add(tempTask);
	    	  }
	      }
	      myReader.close();
	      HashMap<Integer,Integer> mamaLocation = new HashMap<>(); //<id, Location in Task list>
    	  if (!returnTaskList.isEmpty()) {
    		  for (int i = 0; i < returnTaskList.size(); i++) {
    			  if (returnTaskList.get(i).getMamaId()==0) {
    				  mamaLocation.put(returnTaskList.get(i).getId(),i );
    			  }
    		  }
    		  for (Task task : returnTaskList) {
    			  if (task.getMamaId() != 0) {
    				  int temp = mamaLocation.get(task.getMamaId());
    				  returnTaskList.get(temp).addSubTask(task);
    			  }
    		  }
    	  }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
		return returnTaskList;
	}

}
