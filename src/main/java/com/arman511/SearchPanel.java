package com.arman511;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.arman511.extra.SearchTerms;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static SearchPanel instance = null;
	JFrame topLevelFrame;
	TextField keywordField;
	TextField authorField;
	TextField titleField;
	public static void clearInstance() {
		instance = null;
	}

	/**
	 * This gets the singleton of the BudgetBase class.
	 */
	public static SearchPanel getInstance(JFrame frame) {
		instance = null;
		instance = new SearchPanel(frame);

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
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Label label = new Label("Title: ");

		titleField = new TextField();

		Label label2 = new Label("Author: ");

		authorField = new TextField();

		Label label2_1 = new Label("Keywords:");

		keywordField = new TextField();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup().addGap(12)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(48).addComponent(titleField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel_1
								.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
												.addGap(6).addComponent(label2, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(label2_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGap(32)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(keywordField, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
										.addComponent(authorField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(authorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(keywordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label2_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))));
		panel_1.setLayout(gl_panel_1);

		Button searchButton = new Button("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> urlSegmentStrings = new ArrayList<String>();
				if (!titleField.getText().trim().equals("")) {
					urlSegmentStrings.add("title=" + titleField.getText().trim());
				}

				if (!authorField.getText().trim().equals("")) {
					urlSegmentStrings.add("author=" + authorField.getText().trim());
				}
				if (!keywordField.getText().trim().equals("")) {
					urlSegmentStrings.add("keyword=" + keywordField.getText().trim());

				}
				
				String urlSegement = String.join("&", urlSegmentStrings);
				int pages;
				try {
					pages = HtmlManipulator.getSearchResultPagesCount(urlSegement);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(topLevelFrame, "Something went wrong", "IDK",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (pages == 0) {
					JOptionPane.showMessageDialog(topLevelFrame, "No results", "No results", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					SearchResultsViewerAndPicker.createAndShowGUI(
							new SearchTerms(titleField.getText(), authorField.getText(), keywordField.getText()),
							pages);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(topLevelFrame, "Something went wrong", "IDK",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				topLevelFrame.dispatchEvent(new WindowEvent(topLevelFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel_2.add(searchButton);

		Button returnButton = new Button("Back");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topLevelFrame.dispatchEvent(new WindowEvent(topLevelFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
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
