package app.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import dom2app.SimpleTableModel;

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
				//System.out.println(selectIdString);
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
						System.out.println("Exiting with exit choise " + dialogButton);
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

	}// end constructor

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
				System.out.println("### " + pathString);

				SimpleTableModel tblModel = appController.load(pathString, ",");
				showFrameWithTable(tblModel, pathString);
				SimpleRasterModel raster = appController.translateTableModelToRaster(tblModel);
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
				System.out.println("### " + pathString);

				SimpleTableModel tblModel = appController.load(pathString, "\t");
				showFrameWithTable(tblModel, pathString);
				SimpleRasterModel raster = appController.translateTableModelToRaster(tblModel);
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
						//System.out.println(selectIdString);
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
		miLoadCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a target file to save");
			    int destiny = jfc.showSaveDialog(null);
			    if (destiny == JFileChooser.APPROVE_OPTION) {
			    	appController.createReportText(jfc.getSelectedFile()+"");
			    }
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
