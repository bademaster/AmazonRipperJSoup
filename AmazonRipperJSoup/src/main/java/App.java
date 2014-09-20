package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.hibernate.HiberExport;
import main.hibernate.ReviewData;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		HiberExport exp = new HiberExport();
		
				
		
//		ScraperTools test = new ScraperTools();
		String link = "http://www.amazon.de/s/ref=sr_pg_1?rh=n%3A562066%2Cn%3A!569604%2Cn%3A1384526031%2Cn%3A3468301%2Cp_6%3AA3JWKAKR8XB7XF&bbn=3468301&ie=UTF8&qid=1403468947";
		
		Map<String , String> productLinks = new HashMap<String, String>();
//		ArrayList<String[]> revList = test.getReviewList(link);
		
		
//		System.out.println("RevList :" + revList.size());

		productLinks = ScraperTools.getProductLinks(link);
		
		for(Map.Entry e : productLinks.entrySet()){
			ReviewData iData = new ReviewData();
			System.out.println(e.getValue());
			String tempItem = (String) e.getKey();
			String tempLink = (String) e.getValue();
			ArrayList<String[]> revList = ScraperTools.getReviewList(tempLink);
			System.out.println("ArraySize: " + revList.size());
			for(String[] eintrag : revList) {
				iData.setId(Integer.parseInt(eintrag[0]));
				iData.setItemName(tempItem);
				iData.setItemUrl(tempLink);
				iData.setAuthor(eintrag[2]);
				iData.setDate(eintrag[1]);
				iData.setRating(eintrag[3]);
				iData.setText(eintrag[4]);
				iData.setHelpful(eintrag[5]);
				System.out.println("Text: " + eintrag[4]);
				System.out.println("+++++++++++++++++++++++++++++++++++++++++");
				exp.exportToDB(iData);
			}
		}
		
		System.out.println(productLinks.size());
		
//		for(String[] eintrag : revList) {
//			System.out.println("Text: " + eintrag[4]);
//		}
		
		
		// for (Element element2 : temp) {
		// System.out.println(element2.text());
		// }
		
	}

}
