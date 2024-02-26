package com.arman511;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;

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
	Preferences preferences;
	JFrame topLevelFrame;
	JEditorPane editorPane;
	JButton loadByIDButton;
	Fiction bookFiction = null;
	String htmlString;
	int pickedChapter = 0;
	JButton nextChapterButton;
	JButton previousChapterButton;
	JButton changeCurrentBookIndex;
	String colourMode;
	JScrollPane scrollPane;
	JButton settingsButton;
	JButton searchBooksButton;

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
		String codeString = getPreferences();
		initComponents(codeString); // Initialise components
		addListeners(frame);
	}

	/**
	 * Create the panel.
	 */
	public void initComponents(String code) {
		scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);

		previousChapterButton = new JButton("Previous chapter");
		panel.add(previousChapterButton);

		changeCurrentBookIndex = new JButton("Chapter Index");
		panel.add(changeCurrentBookIndex);

		nextChapterButton = new JButton("Next Chapter");
		panel.add(nextChapterButton);


		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel);
		changeCurrentBookIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		setEditorPane(code);

		JPanel panel_1 = new JPanel();
		scrollPane.setRowHeaderView(panel_1);



		searchBooksButton = new JButton("Search books");
		loadByIDButton = new JButton("Load with ID");
		settingsButton = new JButton("Settings");

		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topLevelFrame.dispatchEvent(new WindowEvent(topLevelFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.add(searchBooksButton);
		panel_1.add(loadByIDButton);
		panel_1.add(settingsButton);
		panel_1.add(quitButton);

		setLayout(groupLayout);

	}

	private void setEditorPane(String code) {
		editorPane = new JEditorPane();
		editorPane.setEditable(false);

		HTMLEditorKit kit = new HTMLEditorKit();
		editorPane.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		String textColourString = colourMode.equals("LIGHT") ? "black" : "white";
		String backgroundColourString = colourMode.equals("LIGHT") ? "white" : "black";
		styleSheet.addRule("body {color: " + textColourString + "; font-family: times;"
				+ "margin: 4px; text-align: left;background-color: " + backgroundColourString + "}");
		final String BLUE_COLOR = "blue";
		final String RED_COLOR = "#ff0000";
		final String TABLE_BACKGROUND = "#1c436e";
		final String TABLE_CELL_BORDER = "#557292";

		styleSheet.addRule("h1 {color: " + BLUE_COLOR + ";}");
		styleSheet.addRule("h2 {color: " + RED_COLOR + ";}");
		styleSheet.addRule("table {background-color: " + TABLE_BACKGROUND + "; border-collapse: collapse;}");
		styleSheet.addRule("tr, td, th {border: 1px solid " + TABLE_CELL_BORDER + ";}");

		Document doc = kit.createDefaultDocument();
		editorPane.setDocument(doc);
		if (!code.isEmpty()) {
			try {
				bookFiction = HtmlManipulator.getFiction(code.trim());
			} catch (Exception e2) {

			}
	
			try {
				htmlString = HtmlManipulator.getChapterContent(bookFiction.getChapters().get(pickedChapter));
			} catch (Exception e2) {

			}
		}
		setHtmlText();
		scrollPane.setViewportView(editorPane);
	}

	public void addListeners(final JFrame frame) {
		loadByIDButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Heard");
				String id = JOptionPane.showInputDialog(frame, "Enter the ID of the book", "");

				try {
					bookFiction = HtmlManipulator.getFiction(id.trim());
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

				String titleString = (String) JOptionPane.showInputDialog(frame,
						"Pick the starting chapter from the book " + bookFiction.getTitleString(),
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
				setHtmlText();
				System.out.println("Success");
			}
		});
		nextChapterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bookFiction == null) {
					JOptionPane.showMessageDialog(frame, "No book selected.", "No book", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (pickedChapter == bookFiction.getChapters().size() - 1) {
					JOptionPane.showMessageDialog(frame, "There is no more chapters.", "Last chapter",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				pickedChapter++;
				try {
					htmlString = HtmlManipulator.getChapterContent(bookFiction.getChapters().get(pickedChapter));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Something went wrong", "IDK", JOptionPane.ERROR_MESSAGE);
					return;
				}
				setHtmlText();
			}
		});
		previousChapterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (bookFiction == null) {
					JOptionPane.showMessageDialog(frame, "No book selected.", "No book", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (pickedChapter == 0) {
					JOptionPane.showMessageDialog(frame, "This is the first chapter.", "First chapter",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				pickedChapter--;
				try {
					htmlString = HtmlManipulator.getChapterContent(bookFiction.getChapters().get(pickedChapter));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Something went wrong", "IDK", JOptionPane.ERROR_MESSAGE);
					return;
				}
				setHtmlText();
			}
		});
		
		changeCurrentBookIndex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (bookFiction == null) {
					JOptionPane.showMessageDialog(frame, "No book selected.", "No book", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ArrayList<String> titleArrayList = new ArrayList<String>();
				for (Chapter chapter : bookFiction.getChapters()) {
					titleArrayList.add(chapter.getTitleString());
				}

				String titleString = (String) JOptionPane.showInputDialog(frame,
						"Pick the starting chapter from the book " + bookFiction.getTitleString(), "Starting chapter",
						JOptionPane.QUESTION_MESSAGE, null, titleArrayList.toArray(), titleArrayList.toArray()[0]);
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
				
				setHtmlText();

			}
		});

		settingsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] options = { "LIGHT", "DARK" };
				int colourModeSelection = JOptionPane.showOptionDialog(frame, "Would you like green eggs and ham?",
						"Colour mode", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
				colourMode = options[colourModeSelection];
				int n = JOptionPane.showConfirmDialog(frame, "Would you like to reset your book?", "Reset",
						JOptionPane.YES_NO_OPTION);
				String codeString = bookFiction.getCodeString();
				if (n == 0) {
					htmlString = "";
					bookFiction = null;
					codeString = "";
					pickedChapter = 0;
				}

				setPreferences();
				setEditorPane(codeString);
			}
		});
		
		searchBooksButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = JOptionPane.showInputDialog(frame, "Enter the title of the book", "");
				if (title.isBlank()) {
					JOptionPane.showMessageDialog(frame, "Invalid input of nothing.", "Invalid Value",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				int pagesInteger = HtmlManipulator.getPages(title);

			}
		});
	}

	private void setHtmlText() {
		if (htmlString == null || htmlString.isEmpty()) {
			editorPane.setText(htmlString);
			editorPane.setCaretPosition(0);
			return;
		}
		String content = "<h1>" + bookFiction.getTitleString() + "</h1><h2>"
				+ bookFiction.getChapters().get(pickedChapter).getTitleString() + "</h2>" + htmlString;
		if (pickedChapter + 1 == bookFiction.getChapters().size()) {
			nextChapterButton.setEnabled(false);
		} else {
			nextChapterButton.setEnabled(true);
		}
		if (pickedChapter == 0) {
			previousChapterButton.setEnabled(false);
		} else {
			previousChapterButton.setEnabled(true);
		}
		editorPane.setText(content);
		editorPane.setCaretPosition(0);
		setPreferences();
	}

	public String getPreferences() {
		preferences = Preferences.userRoot().node(this.getClass().getName());
		colourMode = preferences.get("colour_mode", "LIGHT");
		pickedChapter = preferences.getInt("chapter_num", 0);

		return preferences.get("book_code", "");

	}

	public void setPreferences() {
		preferences = Preferences.userRoot().node(this.getClass().getName());
		preferences.put("colour_mode", colourMode);
		if (bookFiction != null) {
			preferences.put("book_code", bookFiction.getCodeString());

		} else {
			preferences.put("book_code", "");
		}
		preferences.putInt("chapter_num", pickedChapter);
	}

	public static void createAndShowGUI() {
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

