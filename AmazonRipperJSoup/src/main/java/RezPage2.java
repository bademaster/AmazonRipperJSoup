package main.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RezPage2 {

//	static String detailUrl = "http://www.amazon.de/Samsung-Smartphone-Touch-Display-Quad-Core-Prozessor-schwarz/dp/B00IKFB4OS/";
//	static String detailUrl = "http://www.amazon.de/Samsung-Galaxy-Tasche-Präsentationsfunktion-Schwarz/dp/B00ILXBHWS";
	static String detailUrl = "http://www.amazon.de/AT-Ambiance-Technology-Q6-Barren-Handy-schwarz/dp/B001GCCZ2M";

	static String rezUrl;

	public static void main(String[] args) throws IOException {

		rezUrl = getRezLink(detailUrl);

		getRezessions(rezUrl);

	}

	public static void getRezessions(String urlIn) throws IOException {
		
		int pageNumber = 1;

		Document doc = Jsoup.connect(urlIn).get();
		boolean hasMoreReviews = true;

		Element metaInfo = doc.getElementById("productSummary");
		
		String ratingCount = metaInfo.select("div[class=tiny]").first().text();
		String averageRating = metaInfo.select("span[class=asinReviewsSummary]").first().text();
		
		System.out.println("Anzahl Bewertungen: " + ratingCount);
		System.out.println("Durschnittsrating: " + averageRating);
		System.out.println("\n\n\n");
		

		
		while (hasMoreReviews) {
			String tempurl = urlIn+"/ref=cm_cr_pr_top_link_next_2&pageNumber=" + pageNumber;
			doc = Jsoup.connect(urlIn+"/ref=cm_cr_pr_top_link_next_2&pageNumber=" + pageNumber).get();
			
			Element reviews = doc.getElementById("productReviews");
		
			Elements singleElements;
			try {
				singleElements = reviews.select("div[style=margin-left:0.5em;]");
			} catch (Exception e) {
				hasMoreReviews = false;
				break;
			}
			
			for (Element element : singleElements) {

				// date of posting
				String date = element.getElementsByTag("nobr").first().text();

				// author
				String author = element
						.select("span[style=font-weight: bold;]").first()
						.text();

				// rating
				String rating = element.select("span[style=margin-right:5px;]")
						.first().text();

				// text
				String text = element.select("div[class=reviewText]").first()
						.text();

				// helpfulness
				String hilfreich = element
						.select("div[style=margin-bottom:0.5em;]").first()
						.text();
				if (!hilfreich
						.contains("Kunden fanden die folgende Rezension hilfreich")) {
					hilfreich = "no rating";
				}

				// for (Element element2 : temp) {
				// System.out.println(element2.text());
				// }

				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("Datum: " + date);
				System.out.println("Autor: " + author);
				System.out.println("Rating: " + rating);
				System.out.println("Text: " + text);
				System.out.println("Hilfreich: " + hilfreich);
				System.out.println("TempUrl " + tempurl);
				System.out.println("PageNr: " + pageNumber);
				System.out.println("-----------------------------------------------");
				System.out.println();
			}
			
			pageNumber++;
		}

	}

	public static String getRezLink(String urlIn) throws IOException {
		Document doc = Jsoup.connect(urlIn).get();

		// Rating
		Elements rating = doc.getElementsByClass("asinReviewsSummary");

		Element rezLink = rating.get(0);

		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(rezLink.html());
		String url = null;

		if (m.find()) {
			url = m.group(1); // this variable should contain the link URL
		}

		return url;
		// System.out.println(url);
		//
		// System.out.println("---------------------------------");

	}

}
