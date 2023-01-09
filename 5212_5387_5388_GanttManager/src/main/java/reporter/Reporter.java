package reporter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import backend.ReportType;
import domainclasses.Task;

class Reporter implements IReporter {

	private String path;
	private ReportType type;
	private ArrayList<Task> tasks;
	private static final String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
	
	public Reporter(String path, List<Task> tasks) {
		this.path = path;
		this.tasks = (ArrayList<Task>) tasks;
	}
	
	public Reporter(String path, List<Task> tasks, ReportType type) {
		this.path = path;
		this.tasks = (ArrayList<Task>) tasks;
		this.type = type;
	}
	
	@Override
	public int makeReportTXT() {
		try (FileWriter myWriter = new FileWriter(path)) {
			for (int i = 0; i < 6; i++) {
				if (i == 5) {
					myWriter.write(columnNames[i] + "\t" + "\n");
				}else {
					myWriter.write(columnNames[i] + "\t");
				}
			}
			for (Task task : tasks) {
				myWriter.write(task.getId() + "\t");
				myWriter.write(task.getName() + "\t");
				myWriter.write(task.getMamaId() + "\t");
				myWriter.write(task.getStart() + "\t");
				myWriter.write(task.getEnd() + "\t");
				myWriter.write(task.getCost() + "\t" + "\n");
			}
			myWriter.flush();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	@Override
	public int makeReportHTML() {
		try (FileWriter myWriter = new FileWriter(path)) {
			myWriter.write("<html>" + "\n");
			myWriter.write("<head>" + "\n");
			myWriter.write("<title>Report</title>" + "\n");
			myWriter.write("</head>" + "\n");
			myWriter.write("<body>" + "\n");
			myWriter.write("<table border=\"1\">" + "\n");
			myWriter.write("<tr>" + "\n");
			for (int i = 0; i < 6; i++) {
				myWriter.write("<th>" + columnNames[i] + "</th>" + "\n");
			}
			myWriter.write("</tr>" + "\n");
			for (Task task : tasks) {
				myWriter.write("<tr>" + "\n");
				myWriter.write("<td>" + task.getId() + "</td>" + "\n");
				myWriter.write("<td>" + task.getName() + "</td>" + "\n");
				myWriter.write("<td>" + task.getMamaId() + "</td>" + "\n");
				myWriter.write("<td>" + task.getStart() + "</td>" + "\n");
				myWriter.write("<td>" + task.getEnd() + "</td>" + "\n");
				myWriter.write("<td>" + task.getCost() + "</td>" + "\n");
				myWriter.write("</tr>" + "\n");
			}
			myWriter.write("</table>" + "\n");
			myWriter.write("</body>" + "\n");
			myWriter.write("</html>" + "\n");
			myWriter.flush();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	@Override
	public int makeReportMD() {
		try (FileWriter myWriter = new FileWriter(path)) {
			for (int i = 0; i < 6; i++) {
				if (i == 5) {
					myWriter.write(columnNames[i] + "\t" + "\n");
				}else {
					myWriter.write(columnNames[i] + "\t");
				}
			}
			for (Task task : tasks) {
				myWriter.write(task.getId() + "\t");
				myWriter.write(task.getName() + "\t");
				myWriter.write(task.getMamaId() + "\t");
				myWriter.write(task.getStart() + "\t");
				myWriter.write(task.getEnd() + "\t");
				myWriter.write(task.getCost() + "\t" + "\n");
			}
			myWriter.flush();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public int createReport() {
		switch (type) {
		case TEXT:
			return makeReportTXT();
		case HTML:
			return makeReportHTML();
		case MD:
			return makeReportMD();
		default:
			return 1;
		}
	}
}
