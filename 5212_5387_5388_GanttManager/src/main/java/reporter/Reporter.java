package reporter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import domainClasses.Task;

import backend.ReportType;

public class Reporter {
	String path;
	ReportType type;
	ArrayList<Task> tasks;
	String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
	
	public Reporter(String path, ArrayList<Task> tasks) {
		this.path = path;
		//this.type = type;
		this.tasks = tasks;
	}
	
	public int makeReportTXT() {
		try {
			path+=".txt";	
		    FileWriter myWriter = new FileWriter(path);
		    for (int i = 0; i < 6; i++) {
		    	if (i == 5) {
		    		myWriter.write(columnNames[i] + "\n");
		    	}else {
		    		myWriter.write(columnNames[i] + "\t");
		    	}
		    }
		    for (Task task : tasks) {
		    	String[] temp = task.stringTask();
		    	for (int i = 0; i < 6; i++) {
			    	if (i == 5) {
			    		myWriter.write(temp[i] + "\n");
			    	}else {
			    		myWriter.write(temp[i] + "\t");
			    	}
			    }
		    }
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		    return -1;
		}
		return 1;
	}
	
	public int makeReportHTML() {
		try {
			path+=".html";	
		    FileWriter myWriter = new FileWriter(path);
		    myWriter.write("<!doctype html>"+"\n"+"<html>"+"\n"+"<head>"+"\n");
		    myWriter.write("<meta http-equiv=\"Content-Type\" content\"text/html; charsel=windows-1253\">"+"\n");
		    myWriter.write("<title>Gantt Project Data</title>" + "\n");
		    myWriter.write("</head>" + "\n" + "<body>" + "\n" + "\n" + "<table>" + "\n");
	
		    for (int i = 0; i < 6; i++) {
		    	if (i == 5) {
		    		myWriter.write("<td>" + columnNames[i] + "</td>" + "\t" + "</tr>" + "\n");
		    	}else if (i == 0){
		    		myWriter.write("<tr>" + "\n" + "<td>" + "\t" + columnNames[i] + "</td>" + "\t");
		    	}else {
		    		myWriter.write("<td>" + columnNames[i] + "</td>" + "\t");
		    	}
		    }
		    for (Task task: tasks) {
		    	String[] temp = task.stringTask();
		    	for(int i = 0; i < temp.length; i++)
			    	if (i == temp.length-1) {
			    		myWriter.write("<td>" + temp[i] + "</td>" + "\t" + "</tr>" + "\n");
			    	}else if (i == 0){
			    		myWriter.write("<tr>" + "\n" + "<td>" + "\t" + temp[i] + "</td>" + "\t");
			    	}else {
			    		myWriter.write("<td>" + temp[i] + "</td>" + "\t");
			    	}
		    }
		    myWriter.write("<body>" + "\n" + "<html>");
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		    return -1;
		}
		return 1;
		
	}
	
	public int makeReportMD() {
		try {
			path+=".md";	
		    FileWriter myWriter = new FileWriter(path);
	
		    for (int i = 0; i < 6; i++) {
		    	if (i == 5) {
		    		myWriter.write("*" + columnNames[i] + "*" + " " + "\n");
		    	}else {
		    		myWriter.write("*" + columnNames[i] + "*" + " ");
		    	}
		    }
		    for (Task task: tasks) {
		    	String[] temp = task.stringTask();
		    	if (task.getMamaId() == 0) {
		    		for(int i = 0; i < temp.length; i++)
				    	if (i == temp.length-1) {
				    		myWriter.write("**" + temp[i] + "**" + " " + "\n");
				    	}else {
				    		myWriter.write("**" + temp[i] + "**" + " ");
				    	}
		    	}else {
			    	for(int i = 0; i < temp.length; i++)
				    	if (i == temp.length-1) {
				    		myWriter.write(temp[i] + " " + "\n");
				    	}else {
				    		myWriter.write(temp[i] + " ");
				    	}
		    	}
		    }
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		    return -1;
		}
		return 1;
	}
}
