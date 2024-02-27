package com.arman511;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static SearchPanel instance = null;
	JFrame topLevelFrame;
	public static void clearInstance() {
		instance = null;
	}

	/**
	 * This gets the singleton of the BudgetBase class.
	 */
	public static SearchPanel getInstance(JFrame frame) {
		if (instance == null) {
			instance = new SearchPanel(frame);
		}
		return instance;
	}

	/**
	 * This is the constuctor for the BudgetBase class.
	 */
	private SearchPanel(JFrame frame) {

		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();

		JPanel panel_2 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		Label label = new Label("Title: ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		panel_1.add(label, gbc_label);

		TextField titleField = new TextField();
		GridBagConstraints gbc_titleField = new GridBagConstraints();
		gbc_titleField.gridx = 2;
		gbc_titleField.gridy = 1;
		panel_1.add(titleField, gbc_titleField);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Label label2 = new Label("Author: ");
		GridBagConstraints gbc_label2 = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 2;
		panel_1.add(label2, gbc_label2);

		TextField authorField = new TextField();
		GridBagConstraints gbc_authorField = new GridBagConstraints();
		gbc_titleField.gridx = 2;
		gbc_titleField.gridy = 2;
		panel_1.add(authorField, gbc_authorField);

		Button searchButton = new Button("Search");
		panel_2.add(searchButton);

		Button returnButton = new Button("Back");
		panel_2.add(returnButton);

		JLabel SearchLabel = new JLabel("Search RRL");
		panel.add(SearchLabel);
		setLayout(groupLayout);
		topLevelFrame = frame; // keep track of top-level frame

	}

	public static void createAndShowGUI() throws Exception {
		JFrame frame = new JFrame("RRL Reader Search");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SearchPanel newContentPane = getInstance(frame);
		newContentPane.setOpaque(true);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setVisible(true);
	}
}
