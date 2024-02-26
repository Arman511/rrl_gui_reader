package com.arman511;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arman511.extra.Chapter;
import com.arman511.extra.Fiction;

public class HtmlManipulator {
	public static Fiction getFiction(String id) throws Exception {
		ArrayList<Chapter> chaptersArrayList = new ArrayList<Chapter>();
		Document doc;
		try {
			doc = Jsoup.connect("https://www.royalroad.com/fiction/" + id).get();
		} catch (Exception e) {
			throw new Exception("Invalid ID");
		}
		Elements chapters = doc.getElementById("chapters").select("a");
		for (int i = 0; i < chapters.size(); i += 2) {
			Element itemElement = chapters.get(i);
			String titleString = itemElement.text();
			String urlString = itemElement.attr("href");
			chaptersArrayList.add(new Chapter(titleString, urlString, i / 2));

		}
		String titleString = doc.select("h1").first().text();
		return new Fiction(titleString, id, chaptersArrayList);
	}

	public static String getChapterContent(Chapter chapter) throws Exception {
		Document document;
		try {
			document = Jsoup.connect("https://www.royalroad.com" + chapter.getUrlString()).get();
		} catch (Exception e) {
			throw new Exception("IDK");
		}
		Elements contentElements = document.getElementsByClass("chapter-content");
		return contentElements.outerHtml();
	}
}

