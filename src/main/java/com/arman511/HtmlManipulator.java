package com.arman511;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arman511.extra.Chapter;
import com.arman511.extra.Fiction;
import com.arman511.extra.SearchTerms;

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

	public static int getPages(String title) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getSearchResultPagesCount(String urlSegement) throws Exception {
		Document doc;
		try {
			doc = Jsoup.connect("https://www.royalroad.com/fictions/search?" + urlSegement).get();
		} catch (Exception e) {
			throw new Exception("Invalid ID");
		}
		if (doc.select("h4").hasClass("font-red-sunglo")) {
			return 0;
		}
		
		int page; 
		try {
			page = Integer.parseInt(doc.getElementsByAttribute("data-page").last().attr("data-page"));
		} catch (Exception e) {
			page = 1;
		}


		// TODO Auto-generated method stub
		return page;
	}

	public static String getSearchResult(SearchTerms terms, int page) throws Exception {
		Document document;
		String url = "https://www.royalroad.com/fictions/search?" + terms.getUrlFragment() + "&page=" + page;
		try {
			document = Jsoup.connect(url).get();
		} catch (Exception e) {
			throw new Exception("IDK");
		}
		Elements nodesElements = document.getElementsByClass("fiction-list-item");
		ArrayList<String> books = new ArrayList<String>();
		int item = 1;
		for (Element node: nodesElements) {
			String titleString = node.select("h2").text();
			String codeString = node.select("a").attr("href").split("/")[2];
			List<String> tagStrings = node.getElementsByClass("fiction-tag").eachText();
			String imgSrc = node.getElementsByAttributeValue("data-type", "cover").attr("src");
			if (imgSrc.equals("/dist/img/nocover-new-min.png")) {
				imgSrc = "https://www.royalroad.com/dist/img/nocover-new-min.png";
			}
			
			String stars = "Stars: " + node.getElementsByClass("star").first().attr("title") + "/5";

			List<String> statsElements = node.getElementsByClass("col-sm-6").select("span").eachText();
			statsElements.add("Last update: " + node.getElementsByClass("stats").select("time").first().text());
			statsElements.add(stars);

			Element descriptionElement = node.getElementById("description-" + codeString);
			String temp = "<div class='item'> <img src='" + imgSrc + "'></img><h1 id='title'>" + item + ": "
					+ titleString + "</h1><h2>"
					+ String.join(", ", statsElements)
					+ "</h2><h3>"
					+ String.join(", ", tagStrings) + "</h3><h4 id='code'>" + codeString + "</h3>"
					+ descriptionElement.outerHtml() + "</div>";
			books.add(temp);
			item++;
		}

		return String.join("<hr>", books);
	}
}

