package com.arman511;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import com.arman511.extra.Chapter;
import com.arman511.extra.Fiction;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JFrame topLevelFrame;
	JEditorPane editorPane;
	JButton loadByIDButton;
	Fiction bookFiction;
	String htmlString;
	int pickedChapter;
	private static MainPanel instance = null;

	public static void clearInstance() {
		instance = null;
	}

	/**
	 * This gets the singleton of the BudgetBase class.
	 */
	public static MainPanel getInstance(JFrame frame) {
		if (instance == null) {
			instance = new MainPanel(frame);
		}
		return instance;
	}

	/**
	 * This is the constuctor for the BudgetBase class.
	 */
	private MainPanel(JFrame frame) {
		topLevelFrame = frame; // keep track of top-level frame
		initComponents(); // Initialise components
		addListeners(frame);
	}

	/**
	 * Create the panel.
	 */
	public void initComponents() {

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);

		JButton btnNewButton_1 = new JButton("Next Chapter");
		panel.add(btnNewButton_1);

		JButton btnNewButton_4 = new JButton("Previous chapter");
		panel.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Chapter Index");
		panel.add(btnNewButton_5);

		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		editorPane = new JEditorPane();
		editorPane.setEditable(false);

		HTMLEditorKit kit = new HTMLEditorKit();
		editorPane.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; text-align: left;}");
		styleSheet.addRule("h1 {color: blue;}");
		styleSheet.addRule("h2 {color: #ff0000;}");
		styleSheet.addRule("table {background-color: blue; border-collapse: collapse;}");
		styleSheet.addRule("tr, td, th {background-color: #cce5ff; border: 1px solid #000;}");

		Document doc = kit.createDefaultDocument();
		editorPane.setDocument(doc);

		scrollPane.setViewportView(editorPane);

		JPanel panel_1 = new JPanel();
		scrollPane.setRowHeaderView(panel_1);

		JButton btnNewButton = new JButton("Previous book");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton searchBooksButton = new JButton("Search books");
		loadByIDButton = new JButton("Load with ID");
		JButton btnNewButton_2 = new JButton("Settings");

		JButton btnNewButton_3 = new JButton("Quit");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.add(btnNewButton);
		panel_1.add(searchBooksButton);
		panel_1.add(loadByIDButton);
		panel_1.add(btnNewButton_2);
		panel_1.add(btnNewButton_3);

		setLayout(groupLayout);

	}

	public void setEditorKit(HTMLEditorKit kit) {
		// TODO Auto-generated method stub
		editorPane.setEditorKit(kit);

	}

	public void setDocument(Document doc) {
		// TODO Auto-generated method stub
		editorPane.setDocument(doc);

	}

	public void setText(String htmlString) {
		// TODO Auto-generated method stub
		editorPane.setText(htmlString);
	}

	public void addListeners(final JFrame frame) {
		loadByIDButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Heard");
				String id = JOptionPane.showInputDialog(frame, "Enter the ID of the book", "");

				try {
					bookFiction = HtmlManipulator.getChapters(id.trim());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Invalid Book ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (bookFiction.getChapters().size() == 0) {
					JOptionPane.showMessageDialog(frame, "Book has no chapters.", "No chapters",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				ArrayList<String> titleArrayList = new ArrayList<String>();
				for (Chapter chapter : bookFiction.getChapters()) {
					titleArrayList.add(chapter.getTitleString());
				}

				String titleString = (String) JOptionPane.showInputDialog(frame, "Pick the starting chapter",
						"Starting chapter", JOptionPane.QUESTION_MESSAGE, null, titleArrayList.toArray(),
						titleArrayList.toArray()[0]);
				for (int i = 0; i < bookFiction.getChapters().size(); i++) {
					if (bookFiction.getChapters().get(i).getTitleString().equals(titleString)) {
						pickedChapter = i;
						break;
					}
				}
				try {
					htmlString = HtmlManipulator.getChapterContent(bookFiction.getChapters().get(pickedChapter));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Something went wrong", "IDK", JOptionPane.ERROR_MESSAGE);
					return;
				}
				editorPane.setText(htmlString);
				editorPane.setCaretPosition(0);
				System.out.println("Success");
			}
		});
	}

	public static void createAndShowGUI() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("RRL Reader");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel newContentPane = getInstance(frame);
		newContentPane.setOpaque(true);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setVisible(true);
	}
}

