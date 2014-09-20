package main.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Detailpage {

//	static String detailUrl = "http://www.amazon.de/Motorola-Smartphone-HD-Display-Megapixel-Quad-Core-Prozessor/dp/B00GUJRQAC/";
	static String detailUrl = "http://www.amazon.de/Samsung-Smartphone-Touch-Display-Quad-Core-Prozessor-schwarz/dp/B00IKFB4OS/";
	
	public static void main(String[] args) throws IOException {
		
		getDetails(detailUrl);
		
	}
	
	public static void getDetails (String urlIn) throws IOException {
		Document doc = Jsoup.connect(detailUrl).get();
		
		// Produktbezeichnung
		Element productName = doc.getElementById("btAsinTitle");
		System.out.println(productName.text());
		
		// Preis
		Elements price = doc.getElementsByClass("priceLarge");
		for (Element pr:price) {
			System.out.println(pr.text());
		}
		
		// Details
		Element details = doc.getElementById("technicalProductFeaturesATF");
		System.out.println(details.text());
		
		// Beschreibung
		Elements description = doc.getElementsByClass("aplus");
		System.out.println(description.first().text());
		
		// Rating
		Elements rating = doc.getElementsByClass("asinReviewsSummary");
		
		Elements rating2 = doc.getElementsByClass("a-link-normal a-text-normal");
		Element asd = doc.getElementById("a-link-normal a-text-normal");
		
		
		Element rezLink = rating.get(0);
		System.out.println(rezLink);
		System.out.println("++++" + rezLink.attr("href"));
		System.out.println("----" + rezLink.text());
		
		System.out.println("---------------------------------");
		
//		Element rezLink2 = rating2.first();
		System.out.println("###### " + rating2.size());
		
//		System.out.println(rating.attr("href"));

//		for (Element rate:rating) {
//			System.out.println(rate.className());
//			System.out.println(rate.text());
//			System.out.println("---");
//		}
//		System.out.println(rating.size());
	}

}
