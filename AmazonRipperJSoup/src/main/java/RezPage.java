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

public class RezPage {

//	static String detailUrl = "http://www.amazon.de/Samsung-Smartphone-Touch-Display-Quad-Core-Prozessor-schwarz/dp/B00IKFB4OS/";
	 static String detailUrl = "http://www.amazon.de/Samsung-Galaxy-Tasche-Präsentationsfunktion-Schwarz/dp/B00ILXBHWS";

	static String rezUrl;

	public static void main(String[] args) throws IOException {

		rezUrl = getRezLink(detailUrl);

		getRezessions(rezUrl);

	}

	public static void getRezessions(String urlIn) throws IOException {

		Document doc = Jsoup.connect(urlIn).get();

		Elements metaInfo = doc.select("div[class=tiny]");
		
		String numberOfRatings = metaInfo.first().text();
		System.out.println(numberOfRatings);
		
		for (Element element2 : metaInfo) {
//			System.out.println("++++++++++++");
//			System.out.println(element2.text());
//			System.out.println("-----------");
		}
		
		Element reviews = doc.getElementById("productReviews");

		Elements singleElements = reviews
				.select("div[style=margin-left:0.5em;]");

		for (Element element : singleElements) {

			// date of posting
			String date = element.getElementsByTag("nobr").first().text();

			// author
			String author = element.select("span[style=font-weight: bold;]")
					.first().text();

			// rating
			String rating = element.select("span[style=margin-right:5px;]")
					.first().text();

			// text
			String text = element.select("div[class=reviewText]").first()
					.text();

			// for (Element element2 : temp) {
			// System.out.println(element2.text());
			// }

//			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("Datum: " + date);
//			System.out.println("Autor: " + author);
//			System.out.println("Rating: " + rating);
//			System.out.println("Text: " + text);
//			System.out.println("-----------------------------------------------");
//			System.out.println();
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
