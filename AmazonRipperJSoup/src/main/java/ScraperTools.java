package main.java;
import java.io.IOException;
import java.util.ArrayList;
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


public class ScraperTools {

//	static String amazonLink = "http://www.amazon.de/s/ref=sr_pg_1?rh=n%3A562066%2Cn%3A!569604%2Cn%3A1384526031%2Cn%3A3468301%2Cp_6%3AA3JWKAKR8XB7XF&bbn=3468301&ie=UTF8&qid=1403468947";
//	static String temp = "http://www.amazon.de/Handys-Smartphones-ohne-Vertrag-Amazon-de/s?ie=UTF8&page=38&rh=n%3A3468301%2Cp_6%3AA3JWKAKR8XB7XF";
//	static String baseUrl = "";
//	static String nextPage = temp;
//	static boolean hasNextPage = true;
//	static Map<String , String> productLinks = new HashMap<String, String>();
	
	
	private static ArrayList<String[]> reviewList;
	private static boolean hasNextPage = true;
	private static String nextPage = "";
	private static int counter = 1;
	
	private static int errorCounter = 0;
	
	static Map<String , String> productLinks = new HashMap<String, String>();
	
	
	
	
	public static Map<String, String> getProductLinks(String amazonLink) throws IOException, InterruptedException {
		getProducts(amazonLink);
		return productLinks;
	}

	public static ArrayList<String[]> getReviewList(String urlIn) throws IOException, InterruptedException {
		getRezessions(getRezLink(urlIn));
		System.out.println("RezLink: " + getRezLink(urlIn));
		return reviewList;
	}
	
	private static ArrayList<String[]> getRezessions(String urlIn) throws IOException, InterruptedException {
	
		reviewList = new ArrayList<String[]>();
		
		
		int counter = 1;
		int pageNumber = 1;

		Document doc;
		try {
			doc = Jsoup.connect(urlIn).get();
		} catch (Exception e) {
			Thread.sleep(2500);
			doc = Jsoup.connect(urlIn).get();
			e.printStackTrace();
		}
		
		boolean hasMoreReviews = true;

		Element metaInfo = doc.getElementById("productSummary");
		
		try {
			String ratingCount = metaInfo.select("div[class=tiny]").first().text();
			String averageRating = metaInfo.select("span[class=asinReviewsSummary]").first().text();
		} catch (Exception e1) {
		}
		
//		System.out.println("Anzahl Bewertungen: " + ratingCount);
//		System.out.println("Durschnittsrating: " + averageRating);
//		System.out.println("\n\n\n");
		

		
		while (hasMoreReviews) {
			
			Thread.sleep(1000);
			
			String tempurl = urlIn+"/ref=cm_cr_pr_top_link_next_2&pageNumber=" + pageNumber;
			
			try {
				doc = Jsoup.connect(tempurl).get();
			} catch (Exception e1) {
				Thread.sleep(2500);
				doc = Jsoup.connect(urlIn).get();
				e1.printStackTrace();
				errorCounter++;
			}
			
			Element reviews = doc.getElementById("productReviews");
		
			Elements singleElements;
			try {
				singleElements = reviews.select("div[style=margin-left:0.5em;]");
			} catch (Exception e) {
				hasMoreReviews = false;
				break;
			}
			
			for (Element element : singleElements) {
				
				String[] array = new String[8];
				array[0] = String.valueOf(counter);
				
				// date of posting
				String date;
				try {
					date = element.getElementsByTag("nobr").first().text();
				} catch (Exception e1) {
					date = "DateParsingError";
					e1.printStackTrace();
				}
				array[1] = date;

				// author
				String author;
				try {
					author = element.select("span[style=font-weight: bold;]").first().text();
				} catch (Exception e) {
					author = "AuthorParsingError";
					e.printStackTrace();
				}
				array[2] = author;

				// rating
				String rating;
				try {
					rating = element.select("span[style=margin-right:5px;]").first().text();
					if (!rating.contains("von")) {
						rating = "no rating";
					}
				} catch (Exception e) {
					rating = "RatingParsingError";
					e.printStackTrace();
				}
				array[3] = rating;
				
				// text
				String text;
				try {
					text = element.select("div[class=reviewText]").first().text();
				} catch (Exception e) {
					text = "TextParsingError";
					e.printStackTrace();
				}
				array[4] = text;
				
				// helpfulness
				String hilfreich = element.select("div[style=margin-bottom:0.5em;]").first().text();
				if (!hilfreich.contains("Kunden fanden die folgende Rezension hilfreich")) {
					hilfreich = "no rating";
				}
				array[5] = hilfreich;
				
				array[6] = tempurl;
				array[7] = String.valueOf(pageNumber);

				reviewList.add(array);
				counter++;

//				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
//				System.out.println("ID: " + counter);
//				System.out.println("Datum: " + date);
//				System.out.println("Autor: " + author);
//				System.out.println("Rating: " + rating);
//				System.out.println("Text: " + text);
//				System.out.println("Hilfreich: " + hilfreich);
//				System.out.println("TempUrl " + tempurl);
//				System.out.println("PageNr: " + pageNumber);
//				System.out.println("-----------------------------------------------");
//				System.out.println();
			}
			
			pageNumber++;
		}
		
		System.out.println("/////////////////////////");
		System.out.println("ErrorCounter: " + errorCounter);
		System.out.println("/////////////////////////");
		
//		System.out.println("Liste hat " + reviewList.size() + " Einträge");
//		for(String[] eintrag : reviewList) {
//			System.out.println("Text: " + eintrag[4]);
//		}
		
		return reviewList;

	}

	private static String getRezLink(String urlIn) throws IOException, InterruptedException {
		Document doc;
		try {
			doc = Jsoup.connect(urlIn).get();
		} catch (Exception e) {
			Thread.sleep(2500);
			doc = Jsoup.connect(urlIn).get();
			e.printStackTrace();
		}

		// Rating
		Elements rating = doc.getElementsByClass("asinReviewsSummary");

		Element rezLink = null;
		try {
			rezLink = rating.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = null;
		try {
			m = p.matcher(rezLink.html());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = null;

		try {
			if (m.find()) {
				url = m.group(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;
	}
	
	
	private static Map<String , String> getProducts(String amazonLink) throws IOException, InterruptedException {
		nextPage = amazonLink;
		
		while (hasNextPage) {
			System.out.println("Hole Seite " + counter++);
			getLinks(nextPage);
//			System.out.println("MapGröße: " + productLinks.size());
			nextPage = getNextPageLink(nextPage);
		}
				
//		System.out.println("Anzahl: " + productLinks.size());
		return productLinks;
	}
	
	
	static void printProductMap () throws InterruptedException {
		for(Iterator<Entry<String, String>>it=productLinks.entrySet().iterator();it.hasNext();){
		     Entry<String, String> entry = it.next();
//		     System.out.println("Product " + entry.getKey());
//		     System.out.println("Lnk " + entry.getValue());
		     it.remove();
//		     System.out.println("Anzahl: " + productLinks.size());
		     Thread.sleep(300);
		 }
	}
	
	private static void getLinks (String urlIn) throws IOException, InterruptedException {
		String hyplink = "";
		Document doc;
		try {
			doc = Jsoup.connect(urlIn).get();
		} catch (Exception e) {
			Thread.sleep(2500);
			doc = Jsoup.connect(urlIn).get();
			e.printStackTrace();
		}

		Element content = doc.getElementById("resultsCol");
		Elements links = content.getElementsByClass("newaps");
		for (Element link : links) {
			Elements hyperl = link.getElementsByAttribute("href");
			for (Element hyp : hyperl) {
				hyplink = hyp.attr("href");
			}
		  String linkText = link.text();
		  
		  productLinks.put(linkText, hyplink);
		  
//		  System.out.println("Text: " + linkText);
//		  System.out.println("Link: " + hyplink);
//		  System.out.println("--------------");
		}
	}
	
	private static String getNextPageLink (String pageLink) throws IOException {
		String next = "";
		try {
			Document doc;
			try {
				doc = Jsoup.connect(nextPage).get();
			} catch (Exception e) {
				Thread.sleep(2500);
				doc = Jsoup.connect(nextPage).get();
				e.printStackTrace();
			}
			Element content = doc.getElementById("pagnNextLink");
			Elements links = content.getElementsByTag("a");
			for (Element link : links) {
//				System.out.println("http://www.amazon.de"+link.attr("href"));
				next = "http://www.amazon.de"+link.attr("href");
//				System.out.println();
			}
		} catch (Exception e) {
			hasNextPage = false;
//			System.err.println("No more pages...");
		}
		return next;
		
	}
	
}