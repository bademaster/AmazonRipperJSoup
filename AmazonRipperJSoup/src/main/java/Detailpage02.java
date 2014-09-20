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

public class Detailpage02 {

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
				
		Element rezLink = rating.get(0);
		System.out.println(rezLink);
		System.out.println("++++" + rezLink.attr("href"));
		System.out.println("----" + rezLink.text());
		
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(rezLink.html());
		String url = null;
		
		if (m.find()) {
		    url = m.group(1); // this variable should contain the link URL
		}
		System.out.println(url);
		
		System.out.println("---------------------------------");
		

		
//		System.out.println(rating.attr("href"));

//		for (Element rate:rating) {
//			System.out.println(rate.className());
//			System.out.println(rate.text());
//			String tmp = rate.html();
//			
//			Pattern p = Pattern.compile("href=\"(.*?)\"");
//			Matcher m = p.matcher(tmp);
//			String url = null;
//			if (m.find()) {
//			    url = m.group(1); // this variable should contain the link URL
//			}
//			
//			System.out.println(url);
//			
////			System.out.println(rate.html());
//			System.out.println("---");
//		}
//		System.out.println(rating.size());
	}

}
