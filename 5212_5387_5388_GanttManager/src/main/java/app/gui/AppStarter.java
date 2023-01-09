package app.gui;

import javax.swing.WindowConstants;

public class AppStarter {

	public static void main(String[] args) {
		JFrameLevel00RootFrame rootFrame = new JFrameLevel00RootFrame();
		rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		rootFrame.setSize(1200, 800); 
		rootFrame.setVisible(true);     

		
	}

}
