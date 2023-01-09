package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import domainclasses.*;

public class FileManager {
	
	private String path;
	private String delimiter;
	private ArrayList<Task> returnTaskList;
	
	public FileManager(String path, String delimiter) {
		this.path = path;
		this.delimiter = delimiter;
		returnTaskList = new ArrayList<>();
	}	
	
	public void setPath(String path) {
		this.path = path;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setReturnTaskList(List<Task> returnTaskList) {
		this.returnTaskList = (ArrayList<Task>) returnTaskList;
	}

	public String getPath() {
		return path;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public List<Task> getReturnTaskList() {
		return returnTaskList;
	}

	public List<Task> giveTasks(){
		try {
	      File file = new File(path);
	      Scanner myReader = new Scanner(file);
	      while (myReader.hasNextLine()) { //Reads the file line by line
	    	  ArrayList<String> taskData; //Stores the data of the task
	    	  String[] line = myReader.nextLine().trim().split(delimiter); //Splits the line into an array of strings
	    	  Task tempTask; //Stores the task
	    	  taskData = new ArrayList<>(Arrays.asList(line)); //Converts the array to an ArrayList

	    	  if (taskData.size() == 6) {  //Checks if the task is simple or complex
	    		  tempTask = new SimpleTask(Integer.parseInt(taskData.get(0)), //Creates the task
	    				  taskData.get(1),
	    				  Integer.parseInt(taskData.get(2)),
	    				  Integer.parseInt(taskData.get(3)),
	    				  Integer.parseInt(taskData.get(4)),
	    				  Double.parseDouble(taskData.get(5)));
	    		  returnTaskList.add(tempTask);
	    	  }else if (taskData.size() == 3) { //Checks if the task is simple or complex
	    		  tempTask = new ComplexTask(Integer.parseInt(taskData.get(0)), //Creates the task
	    				  taskData.get(1),
	    				  Integer.parseInt(taskData.get(2)));
	    		  returnTaskList.add(tempTask);
				  System.out.println(tempTask);
	    	  }
	      }
	      myReader.close();
	      HashMap<Integer,Integer> mamaLocation = new HashMap<>(); //<id, Location in Task list>
    	  if (!returnTaskList.isEmpty()) { //Checks if the list is empty
    		  for (int i = 0; i < returnTaskList.size(); i++) { //Finds the location of the complex tasks
    			  if (returnTaskList.get(i).getMamaId()==0) { //Checks if the task is complex
    				  mamaLocation.put(returnTaskList.get(i).getId(),i ); //Adds the id and location to the hashmap
    			  }
    		  }
    		  for (Task task : returnTaskList) { //Adds the subtasks to the complex tasks
    			  if (task.getMamaId() != 0) { //Checks if the task is simple
    				  int temp = mamaLocation.get(task.getMamaId()); //Finds the location of the complex task
    				  returnTaskList.get(temp).addSubTask(task); //Adds the simple task to the complex task
    			  }
    		  }
    	  }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
		return returnTaskList;
	}

}
