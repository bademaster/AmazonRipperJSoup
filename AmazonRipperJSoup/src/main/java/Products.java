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

public class Products {

	static String amazonLink = "http://www.amazon.de/s/ref=sr_pg_1?rh=n%3A562066%2Cn%3A!569604%2Cn%3A1384526031%2Cn%3A3468301%2Cp_6%3AA3JWKAKR8XB7XF&bbn=3468301&ie=UTF8&qid=1403468947";
	
	static String temp = "http://www.amazon.de/Handys-Smartphones-ohne-Vertrag-Amazon-de/s?ie=UTF8&page=38&rh=n%3A3468301%2Cp_6%3AA3JWKAKR8XB7XF";
	static String baseUrl = "";
	static String nextPage = amazonLink;
	static boolean hasNextPage = true;
	static Map<String , String> productLinks = new HashMap<String, String>();
	static int counter = 1;
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		while (hasNextPage) {
			System.out.println("Hole Seite " + counter++);
			getLinks(nextPage);
			System.out.println("MapGröße: " + productLinks.size());
			nextPage = getNextPageLink(nextPage);
		}
		
		Thread.sleep(1500);
		printProductMap();
		
		System.out.println("Anzahl: " + productLinks.size());
	}
	
	
	static void printProductMap () throws InterruptedException {
		for(Iterator<Entry<String, String>>it=productLinks.entrySet().iterator();it.hasNext();){
		     Entry<String, String> entry = it.next();
		     System.out.println("Product " + entry.getKey());
		     System.out.println("Lnk " + entry.getValue());
		     it.remove();
		     System.out.println("Anzahl: " + productLinks.size());
		     Thread.sleep(300);
		 }
	}
	
	static void getLinks (String urlIn) throws IOException {
		String hyplink = "";
		Document doc = Jsoup.connect(urlIn).get();

		Element content = doc.getElementById("resultsCol");
		Elements links = content.getElementsByClass("newaps");
		for (Element link : links) {
			Elements hyperl = link.getElementsByAttribute("href");
			for (Element hyp : hyperl) {
				hyplink = hyp.attr("href");
			}
		  String linkText = link.text();
		  
		  productLinks.put(linkText, hyplink);
		  
		  System.out.println("Text: " + linkText);
		  System.out.println("Link: " + hyplink);
		  System.out.println("--------------");
		}
	}
	
	
	static String getNextPageLink (String pageLink) throws IOException {
		String next = "";
		try {
			Document doc = Jsoup.connect(nextPage).get();
			Element content = doc.getElementById("pagnNextLink");
			Elements links = content.getElementsByTag("a");
			for (Element link : links) {
				System.out.println("http://www.amazon.de"+link.attr("href"));
				next = "http://www.amazon.de"+link.attr("href");
//			nextPage = "http://www.amazon.de"+link.attr("href");
				System.out.println();
			}
		} catch (Exception e) {
			hasNextPage = false;
			System.err.println("No more pages...");
		}
		return next;
		
	}

}
