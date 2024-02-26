package com.arman511;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainPanel.createAndShowGUI();
			}
		});
    }
}
