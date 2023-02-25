package app.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


import app.AppController;
import app.SimpleRasterModel;
import app.gui.jtableview.JTableViewer;
import domtoapp.SimpleTableModel;

public class JFrameLevel00RootFrame extends JFrame {
	private final class ActionListenerImplementation implements ActionListener {
		private final class AbstractActionExtension extends AbstractAction {
			private final JDialog dialog;
			private final JTextField textfield1;
			/**
			 * 
			 */
			private static final long serialVersionUID = -1319422410622362398L;

			private AbstractActionExtension(String name, JDialog dialog, JTextField textfield1) {
				super(name);
				this.dialog = dialog;
				this.textfield1 = textfield1;
			}

			public void actionPerformed(ActionEvent e) {
				selectIdString = textfield1.getText();
				dialog.dispose();
			}
		}

		private final JFrameLevel00RootFrame f;
		int selectedId = -1;
		String selectIdString = "";

		private ActionListenerImplementation(JFrameLevel00RootFrame f) {
			this.f = f;
		}

		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog(f, "Dialog", true);
			JPanel p = new JPanel();
			JTextField textfield1 = new JTextField(10);
			p.add(textfield1);
			JButton okayButton;
			p.add(okayButton = new JButton(new AbstractActionExtension("OK", dialog, textfield1)));
			dialog.add(p);
			dialog.pack();
			dialog.setVisible(true);

			selectedId = Integer.valueOf(selectIdString);
			SimpleTableModel tblModel = appController.getById(selectedId);
			showFrameWithTable(tblModel, "Filter for: "+selectIdString);

		}//end actionPerformed
	}

	private static final long serialVersionUID = 1L;
	private final AppController appController;
	private final JDesktopPane theDesktop;

	public JFrameLevel00RootFrame() {
		super("Gantt Manager with Java Swing");
		appController = new AppController();

		// add desktop pane to frame
		theDesktop = new JDesktopPane();
		this.add(theDesktop);

		// Handle menubar
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);

		JMenu csvMenu = new JMenu("File");
		menubar.add(csvMenu);


		JMenuItem miLoadTsv = new JMenuItem("Load TSV");
		csvMenu.add(miLoadTsv);
		this.addLoadTsvActionListener(miLoadTsv);

		JMenuItem miLoadCsv = new JMenuItem("Load CSV");
		csvMenu.add(miLoadCsv);
		this.addLoadCsvActionListener(miLoadCsv);
		
		JMenuItem miExit = new JMenuItem("Exit");
		csvMenu.add(miExit);
		miExit.addActionListener(
				e -> { 
					int dialogButton =  JOptionPane.showConfirmDialog(null,
							"Are you sure you want to exit?","Warning",JOptionPane.OK_CANCEL_OPTION);

					if(dialogButton == JOptionPane.OK_OPTION){ 
						System.exit(NORMAL);
					} 	
				}
				);

		JMenu csvFilter = new JMenu("Filters");
		menubar.add(csvFilter);

		JMenuItem miTopLevel = new JMenuItem("Top Level Tasks");
		csvFilter.add(miTopLevel);
		this.addTopLEvelActionListener(miTopLevel);

		JMenuItem miFilterId = new JMenuItem("Filter by Id");
		csvFilter.add(miFilterId);
		this.addFilterByIdActionListener(miFilterId);

		JMenuItem miFilterPrefix = new JMenuItem("Filter by Prefix");
		csvFilter.add(miFilterPrefix);
		this.addFilterByPrefixActionListener(miFilterPrefix);

		JMenu reporterMenu = new JMenu("Report");
		menubar.add(reporterMenu);

		JMenuItem miReportTxt = new JMenuItem("Report TXT");
		reporterMenu.add(miReportTxt);
		this.addReportTxtActionListener(miReportTxt);

		JMenuItem miReportMd = new JMenuItem("Report Markdown");
		reporterMenu.add(miReportMd);
		this.addReportMdActionListener(miReportMd);

		JMenuItem miReportHtml = new JMenuItem("Report HTML");
		reporterMenu.add(miReportHtml);
		this.addReportHtmlActionListener(miReportHtml);

		JMenu taskSettings = new JMenu("Settings");
		menubar.add(taskSettings);

		JMenuItem miTaskSettings5 = new JMenuItem("Reload Table");
		taskSettings.add(miTaskSettings5);
		this.addTaskSettings5ActionListener(miTaskSettings5);

		JMenu miTaskSettings = new JMenu("Task Settings");
		taskSettings.add(miTaskSettings);

		JMenuItem miTaskSettings2 = new JMenuItem("Add Task");
		miTaskSettings.add(miTaskSettings2);
		this.addTaskSettings2ActionListener(miTaskSettings2);

		JMenuItem miTaskSettings3 = new JMenuItem("Delete Task");
		miTaskSettings.add(miTaskSettings3);
		this.addTaskSettings3ActionListener(miTaskSettings3);

		JMenuItem miTaskSettings4 = new JMenuItem("Update Task");
		miTaskSettings.add(miTaskSettings4);
		this.addTaskSettings4ActionListener(miTaskSettings4);

		JMenu helpMenu = new JMenu("Help");
		menubar.add(helpMenu);

		JMenuItem miHelp = new JMenuItem("Help");
		helpMenu.add(miHelp);

		JMenuItem miAbout = new JMenuItem("About");
		helpMenu.add(miAbout);

	}// end constructor

	private void addTaskSettings5ActionListener(JMenuItem miTaskSettings5) {
		
		miTaskSettings5.addActionListener(event -> {
			JInternalFrame[] frames = theDesktop.getAllFrames();
			for (JInternalFrame frame : frames) {
				frame.dispose();
			}
			// If the user clicks on the menu item Reload Table, then the table is reloaded
			// with the data from the file
			SimpleTableModel tblModel = appController.getTaskList();
			SimpleRasterModel rasterModel = appController.translateTableModelToRaster(tblModel);
			showFrameWithTable(tblModel, "All Tasks");
			showFrameWithRaster(rasterModel, getTitle());
		});

	}

	private void addTaskSettings4ActionListener(JMenuItem miTaskSettings4) 
	{
		// Make an action listener for the menu item Delete Task
		miTaskSettings4.addActionListener(event -> {
			if (appController.getTaskList() == null || appController.getTaskList().getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "No tasks to update", "Error", JOptionPane.WARNING_MESSAGE);
			} else {
				JDialog dialog = new JDialog(this, "Update Task", true);
				JPanel p = new JPanel();
				JTextField textfield1 = new JTextField(10);
				p.add(textfield1);
				JButton okayButton;
				p.add(okayButton = new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						String selectIdString = textfield1.getText();
						int selectedId = Integer.parseInt(selectIdString);
						// Ask the user for what to update
						final String [] options = {"TaskId","TaskText","MamaId","Start","End","Cost"};
						int choice = JOptionPane.showOptionDialog(null, "What do you want to update?", "Update Task", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
						// Ask the user for the new value
						String newValue = JOptionPane.showInputDialog("Enter the new value");
						// Update the task
						appController.updateTask(selectedId, choice, newValue);
						// Reload the table with the current task list in the app controller
						SimpleTableModel taskList = appController.getTaskList();
						JInternalFrame[] frames = theDesktop.getAllFrames();
						for (JInternalFrame frame : frames) {
							frame.dispose();
						}
						if (taskList != null) {
							taskList.fireTableDataChanged();
							showFrameWithTable(taskList, "Task List");
						}
						dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);
			}

		});
	}

	private void addTaskSettings3ActionListener(JMenuItem miTaskSettings3) {
		// Make an action listener for the menu item Delete Task
		miTaskSettings3.addActionListener(event -> {
			if (appController.getTaskList() == null || appController.getTaskList().getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "No tasks to delete", "Error", JOptionPane.WARNING_MESSAGE);		
			} else {
				JDialog dialog = new JDialog(this, "Delete Task", true);
				JPanel p = new JPanel();
				// Text field to enter the id of the task to delete, put a label on it
				JLabel label1 = new JLabel("Enter the id of the task to delete");
				p.add(label1);
				JTextField textfield1 = new JTextField(10);
				p.add(textfield1);
				JButton okayButton;
				p.add(okayButton = new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						String selectIdString = textfield1.getText();
						int selectedId = Integer.parseInt(selectIdString);
						appController.deleteTask(selectedId);
						// If the task deleted was a top level task, then all subtasks are deleted
						// Reload the table with the current task list in the app controller
						if (Arrays.equals(appController.getTaskData(selectedId), new String[0])) {
							appController.deleteSubTasks(selectedId);
						}
						SimpleTableModel taskList = appController.getTaskList();
						SimpleRasterModel rasterModel = appController.translateTableModelToRaster(taskList);  
						JInternalFrame[] frames = theDesktop.getAllFrames();
						for (JInternalFrame frame : frames) {
							frame.dispose();
						}
						if (taskList != null) {
							taskList.fireTableDataChanged();
							List<String[]> taskListData = new ArrayList<>();
							for (int i = 0; i < taskList.getRowCount(); i++) {
								String[] taskData = new String[taskList.getColumnCount()];
								for (int j = 0; j < taskList.getColumnCount(); j++) { // for each column
									taskData[j] = (String) taskList.getValueAt(i, j);
								}
								taskListData.add(taskData);
							}			
							String regex = "^(\\w+)=(\\d+)$";  
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher("myVariable=123");	
							if (matcher.find()) {
								Collections.sort(taskListData, (o1, o2) -> Integer.valueOf(o1[0]).compareTo(Integer.valueOf(o2[0])));  // sort by task id
							}
							showFrameWithTable(taskList, "Task List");
							showFrameWithRaster(rasterModel, getTitle());
						}

						dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);

			}
		});

	}

	private void addTaskSettings2ActionListener(JMenuItem miTaskSettings2) {
		// Make an action listener for the menu item Add Task
		miTaskSettings2.addActionListener(event -> {
			JDialog dialog = new JDialog(this, "Add Task", true);
			JPanel p = new JPanel();
			// Text field to enter the task text, put a label on it
			JLabel label1 = new JLabel("Enter the task text");
			p.add(label1);
			JTextField textfield1 = new JTextField(10);
			p.add(textfield1);
			// Text field to enter the mama id, put a label on it
			JLabel label2 = new JLabel("Enter the mama id");
			p.add(label2);
			JTextField textfield2 = new JTextField(10);
			p.add(textfield2);
			// Text field to enter the start date, put a label on it
			JLabel label3 = new JLabel("Enter the start date");
			p.add(label3);
			JTextField textfield3 = new JTextField(10);
			p.add(textfield3);
			// Text field to enter the end date, put a label on it
			JLabel label4 = new JLabel("Enter the end date");
			p.add(label4);
			JTextField textfield4 = new JTextField(10);
			p.add(textfield4);
			// Text field to enter the cost, put a label on it
			JLabel label5 = new JLabel("Enter the cost");
			p.add(label5);
			JTextField textfield5 = new JTextField(10);
			p.add(textfield5);
			// Text field to enter the task id, put a label on it
			JLabel label6 = new JLabel("Enter the task id");
			p.add(label6);
			JTextField textfield6 = new JTextField(10);
			p.add(textfield6);
			JButton okayButton;
			p.add(okayButton = new JButton(new AbstractAction("OK") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					String taskText = textfield1.getText();
					String mamaIdString = textfield2.getText();
					String startDateString = textfield3.getText();
					String endDateString = textfield4.getText();
					String costString = textfield5.getText();
					String taskIdString = textfield6.getText();
					if ((taskIdString.isEmpty() && taskText.isEmpty() && mamaIdString.isEmpty()) || startDateString.isEmpty() || endDateString.isEmpty() || costString.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.WARNING_MESSAGE);
					} else {
						int mamaId = Integer.parseInt(mamaIdString);
						int taskId = Integer.parseInt(taskIdString);
						int cost = Integer.parseInt(costString);
						if (mamaId != 0) {
							String[] mamaTaskData = appController.getTaskData(mamaId); // Get the mama task data
							String mamaStartDateString = mamaTaskData[1];
							String mamaEndDateString = mamaTaskData[2];
							String mamaCostString = mamaTaskData[3];
							Double mamaCost = Double.parseDouble(mamaCostString);
							// If the task start date is before the mama task start date, set the mama task start date to the task start date
							if (startDateString.compareTo(mamaStartDateString) < 0) {
								mamaStartDateString = startDateString;
							}
							// If the task end date is after the mama task end date, set the mama task end date to the task end date
							if (endDateString.compareTo(mamaEndDateString) > 0) {
								mamaEndDateString = endDateString;
							}
							// Add the task cost to the mama task cost
							mamaCost += cost;
							// Update the mama task with the new start date, end date, and cost
							appController.updateMamaTask(mamaId, mamaStartDateString, mamaEndDateString, mamaCost);
						}
						appController.addTask(taskText, mamaId, startDateString, endDateString, cost, taskId);
						SimpleTableModel taskList = appController.getTaskList();
						if (taskList != null) {
							taskList.fireTableDataChanged();
							// take the simple table model data and put it in list with each row as a task object with its attributes separated by tabs
							List<String[]> taskListData = new ArrayList<>();
							for (int i = 0; i < taskList.getRowCount(); i++) {
								String[] taskData = new String[taskList.getColumnCount()];
								for (int j = 0; j < taskList.getColumnCount(); j++) { // for each column
									taskData[j] = (String) taskList.getValueAt(i, j);
								}
								taskListData.add(taskData);
							}						
							String regex = "^(\\w+)=(\\d+)$";  
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher("myVariable=123");	
							if (matcher.find()) {
								Collections.sort(taskListData, (o1, o2) -> Integer.valueOf(o1[0]).compareTo(Integer.valueOf(o2[0])));  // sort by task id
							}

							final String [] columnNames = {"TaskId","TaskText","MamaId","Start","End","Cost"};
							var taskListSortedById = new SimpleTableModel("Tasks Table", "Tasks Table", columnNames, taskListData);
							var taskListRaster = appController.translateTableModelToRaster(taskListSortedById);
							// Dispose any previous frames and show the new frame
							// Check if any internal frames are open, if so dispose of them
							JInternalFrame[] frames = theDesktop.getAllFrames();
							for (JInternalFrame frame : frames) {
								frame.dispose();
							}
							showFrameWithTable(taskListSortedById, "Task List");
							showFrameWithRaster(taskListRaster, "Task List Graph");
						}
						dialog.dispose();
					}
				}
			}));
			dialog.add(p);
			dialog.pack();
			dialog.setVisible(true);

		});
	}

	private void addLoadCsvActionListener(JMenuItem miLoadCsv) {
		// Make an action listener for the menu item to load a csv file
		miLoadCsv.addActionListener(event -> {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a csv file");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
			jfc.addChoosableFileFilter(filter);

			// To add *.* as a possible filter
			jfc.setAcceptAllFileFilterUsed(true);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String pathString = jfc.getSelectedFile().getPath();

				var tblModel = appController.load(pathString, ",");
				var raster = appController.translateTableModelToRaster(tblModel);
				showFrameWithTable(tblModel, pathString);
				showFrameWithRaster(raster, pathString);
			}
		});
	}

	private void addLoadTsvActionListener(JMenuItem miLoadCsv) {
		miLoadCsv.addActionListener(event -> {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a tsv file");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TSV files", "tsv");
			jfc.addChoosableFileFilter(filter);

			// To add *.* as a possible filter
			jfc.setAcceptAllFileFilterUsed(true);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String pathString = jfc.getSelectedFile().getPath();

				var tblModel = appController.load(pathString, "\t");
				var raster = appController.translateTableModelToRaster(tblModel);
				showFrameWithTable(tblModel, pathString);
				showFrameWithRaster(raster, pathString);
			}
		});
	}// end AddLoadCSVListener

	private void addTopLEvelActionListener(JMenuItem miLoadCsv) {
		miLoadCsv.addActionListener(event -> {
				SimpleTableModel tblModel = appController.getTopLevel();
				showFrameWithTable(tblModel, "Top Level Tasks");
				SimpleRasterModel raster = appController.translateTableModelToRaster(tblModel);
				showFrameWithRaster(raster, "Top Level Tasks");
			});
	}// end AddLoadCSVListener


	private void addFilterByIdActionListener(JMenuItem miFilterId) {
		JFrameLevel00RootFrame f = this;
		miFilterId.addActionListener(new ActionListenerImplementation(f)); //end addAction Listener
	}


	
	private void addFilterByPrefixActionListener(JMenuItem miFilterId) {
		JFrameLevel00RootFrame f = this;
		miFilterId.addActionListener(new ActionListener() {

			String selectPrefixString = "";

			public void actionPerformed(ActionEvent event) {
				JDialog dialog = new JDialog(f, "Dialog", true);
				JPanel p = new JPanel();
				JTextField textfield1 = new JTextField(10);
				p.add(textfield1);
				JButton okayButton;
				p.add(okayButton = new JButton(new AbstractAction("OK") {
					/**
					 * 
					 */
					private static final long serialVersionUID = -7171322570068446527L;

					public void actionPerformed(ActionEvent e) {
						selectPrefixString = textfield1.getText();
						dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);

				SimpleTableModel tblModel = appController.getByPrefix(selectPrefixString);
				showFrameWithTable(tblModel, "Filter for: "+ selectPrefixString);

			}//end actionPerformed
		}); //end addAction Listener
	}
	
	
	
	private void addReportTxtActionListener(JMenuItem miLoadCsv) {
		miLoadCsv.addActionListener(event -> {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a target file to save");
		    int destiny = jfc.showSaveDialog(null);
		    if (destiny == JFileChooser.APPROVE_OPTION) {
		    	appController.createReportText(jfc.getSelectedFile()+"");
		    }
		});
	}// end addReportTxtActionListener

	
	private void addReportMdActionListener(JMenuItem miLoadCsv) {
		miLoadCsv.addActionListener(event -> {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a target file to save");
		    int destiny = jfc.showSaveDialog(null);
		    if (destiny == JFileChooser.APPROVE_OPTION) {
		    	appController.createReportMd(jfc.getSelectedFile()+"");
		    }
		});
	}// end addReportMdActionListener
	
	private void addReportHtmlActionListener(JMenuItem miLoadCsv) {
		miLoadCsv.addActionListener(event -> {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select a target file to save");
		    int destiny = jfc.showSaveDialog(null);
		    if (destiny == JFileChooser.APPROVE_OPTION) {
		    	appController.createReportHtml(jfc.getSelectedFile()+"");
		    }
		});
	}// end addReportTxtActionListener
	
	
	private void showFrameWithTable(SimpleTableModel tblModel, String title) {
		JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
		JTableViewer jTableViewer;

		jTableViewer = new JTableViewer(tblModel);
		frame.add(jTableViewer, BorderLayout.CENTER);
		jTableViewer.createAndShowJTable();
		frame.pack(); // set internal frame to size of contents
		theDesktop.add(frame); // attach internal frame
		frame.setVisible(true); // show internal frame
	}
	
	private void showFrameWithRaster(SimpleRasterModel rasterModel, String title) {
		JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
		JTableViewer jTableViewer;

		jTableViewer = new JTableViewer(rasterModel);
		frame.add(jTableViewer, BorderLayout.CENTER);
		jTableViewer.createAndShowJTable();
		frame.pack(); // set internal frame to size of contents
		theDesktop.add(frame); // attach internal frame
		frame.setVisible(true); // show internal frame
	}

}// end class
