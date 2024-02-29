package com.arman511;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
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

import org.jsoup.Jsoup;

import com.arman511.extra.Chapter;
import com.arman511.extra.Fiction;
import com.arman511.extra.SearchTerms;

public class SearchResultsViewerAndPicker extends JPanel {
	int page = 1;
	private static final long serialVersionUID = 1L;
	static int pages;
	private static SearchResultsViewerAndPicker instance = null;
	JFrame topLevelFrame;
	JEditorPane editorPane;
	String htmlString;
	JScrollPane scrollPane;
	List<String> codes;
	List<String> titleStrings;
	static SearchTerms terms;

	public static SearchResultsViewerAndPicker getInstance(JFrame frame) {
		instance = null;
		instance = new SearchResultsViewerAndPicker(frame);
		return instance;
	}

	/**
	 * This is the constuctor for the BudgetBase class.
	 */
	private SearchResultsViewerAndPicker(JFrame frame) {


		scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);

		final JLabel lblNewLabel = new JLabel("Search for: " + terms.searchTitle() + " Page " + page + " of " + pages);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		scrollPane.setRowHeaderView(panel_1);

		final JButton nextPage = new JButton("Next page");
		if (page == pages) {
			nextPage.setEnabled(false);
		}

		final JButton previousPage = new JButton("Back page");
		if (page == 1) {
			previousPage.setEnabled(false);
		}

		nextPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page++;
				if (page == pages) {
					nextPage.setEnabled(false);
				}
				previousPage.setEnabled(true);
				lblNewLabel.setText("Search for: " + terms.searchTitle() + " Page " + page + " of " + pages);
				setEditorPane();
			}
		});
		previousPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page--;
				if (page == 1) {
					previousPage.setEnabled(false);
				}
				nextPage.setEnabled(true);
				lblNewLabel.setText("Search for: " + terms.searchTitle() + " Page " + page + " of " + pages);
				setEditorPane();
			}
		});


		JButton Back = new JButton("Back");
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JButton selectBook = new JButton("Select Book");
		selectBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedBookTitle = (String) JOptionPane.showInputDialog(topLevelFrame,
						"Pick the book you want", "Select Book", JOptionPane.QUESTION_MESSAGE, null,
						titleStrings.toArray(), titleStrings.toArray()[0]);
				int position = titleStrings.indexOf(selectedBookTitle);
				String codeString = codes.get(position);
				Fiction bookFiction;
				try {
					bookFiction = HtmlManipulator.getFiction(codeString.trim());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(topLevelFrame, "Invalid Book ID.", "Invalid ID",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (bookFiction.getChapters().size() == 0) {
					JOptionPane.showMessageDialog(topLevelFrame, "Book has no chapters.", "No chapters",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				ArrayList<String> titleArrayList = new ArrayList<String>();
				for (Chapter chapter : bookFiction.getChapters()) {
					titleArrayList.add(chapter.getTitleString());
				}

				String titleString = (String) JOptionPane.showInputDialog(topLevelFrame,
						"Pick the starting chapter from the book " + bookFiction.getTitleString(), "Starting chapter",
						JOptionPane.QUESTION_MESSAGE, null, titleArrayList.toArray(), titleArrayList.toArray()[0]);

				int i = 0;
				int pickedChapter = 0;
				for (Chapter chapter : bookFiction.getChapters()) {
					if (chapter.getTitleString().equals(titleString)) {
						pickedChapter = i;
						break;
					}
					i++;
				}
				String resultString;
				try {
					resultString = HtmlManipulator.getChapterContent(bookFiction.getChapters().get(pickedChapter));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(topLevelFrame, "Something went wrong", "IDK",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				MainPanel.htmlString = resultString;
				MainPanel.pickedChapter = pickedChapter;
				MainPanel.bookFiction = bookFiction;
				MainPanel.setHtmlFromSearch();
				topLevelFrame.dispatchEvent(new WindowEvent(topLevelFrame, WindowEvent.WINDOW_CLOSING));

			}
		});
		panel_1.add(selectBook);
		panel_1.add(nextPage);
		panel_1.add(previousPage);
		panel_1.add(Back);

		setEditorPane();
		setLayout(groupLayout);

		topLevelFrame = frame;
	}

	private void setEditorPane() {
		try {
			htmlString = HtmlManipulator.getSearchResult(terms, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(topLevelFrame, "No results", "No results", JOptionPane.ERROR_MESSAGE);
		}
		org.jsoup.nodes.Document document = Jsoup.parse(htmlString);
		codes = document.getElementsByTag("h4").attr("id", "code").eachText();
		titleStrings = document.getElementsByTag("h1").attr("id", "title").eachText();
		editorPane = new JEditorPane();
		editorPane.setEditable(false);

		HTMLEditorKit kit = new HTMLEditorKit();
		editorPane.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		Preferences preferences = MainPanel.getInstance(topLevelFrame).preferences;
		String colourMode = preferences.get("colour_mode", "LIGHT");
		String textColourString = colourMode.equals("LIGHT") ? "black" : "white";
		String backgroundColourString = colourMode.equals("LIGHT") ? "white" : "black";
		styleSheet.addRule("body {color: " + textColourString + "; font-family: times;"
				+ "margin: 4px; text-align: left;background-color: " + backgroundColourString + "}");
		styleSheet.addRule("img{width:25%;}");
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

		setHtmlText();
		scrollPane.setViewportView(editorPane);
	}

	private void setHtmlText() {
		if (htmlString == null || htmlString.isEmpty()) {
			editorPane.setText(htmlString);
			editorPane.setCaretPosition(0);
			return;
		}
		editorPane.setText(htmlString);
		editorPane.setCaretPosition(0);
	}
	/**
	 * Create the panel.
	 */

	public static void createAndShowGUI(SearchTerms gTerms, int gPages) throws Exception {
		pages = gPages;
		terms = gTerms;
		JFrame frame = new JFrame("RRL Reader Search");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SearchResultsViewerAndPicker newContentPane = getInstance(frame);
		newContentPane.setOpaque(true);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setVisible(true);
	}
}
