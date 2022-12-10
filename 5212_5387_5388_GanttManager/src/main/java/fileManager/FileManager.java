package fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import domainClasses.*;

public class FileManager {
	String path;
	String delimiter;
	ArrayList<Task> returnTaskList;
	
	public FileManager(String path, String delimiter) {
		this.path = path;
		this.delimiter = delimiter;
		returnTaskList = new ArrayList<Task>();
	}	
	
	public ArrayList<Task> giveTasks(){
		try {
	      File file = new File(path);
	      Scanner myReader = new Scanner(file);
	      while (myReader.hasNextLine()) {
	        /*String data = myReader.nextLine();
	        System.out.println(data);*/
	    	  ArrayList<String> taskData = new ArrayList<String>();
	    	  String[] line = myReader.nextLine().split(delimiter);
	    	  Task tempTask;
	    	  for (String s : line) {
	    		  //System.out.println(s);
	    		  taskData.add(s);
	    	  }
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
    	  if (returnTaskList.size() != 0) {
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
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		return returnTaskList;
	}

}
