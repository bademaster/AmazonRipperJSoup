package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import main.hibernate.HiberExport;
import main.hibernate.ReviewData;

public class App2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		int counter = 1;
		
		HiberExport exp = new HiberExport();

		
//		SessionFactory sessionFactory = new Configuration()
//	    .configure("bin/hibernate.cfg.xml")
//	    .buildSessionFactory();
		

//		ScraperTools test = new ScraperTools();
		String link = "http://www.amazon.de/s/ref=sr_pg_1?rh=n%3A562066%2Cn%3A!569604%2Cn%3A1384526031%2Cn%3A3468301%2Cp_6%3AA3JWKAKR8XB7XF&bbn=3468301&ie=UTF8&qid=1403468947";
		
		Map<String , String> productLinks = new HashMap<String, String>();
//		ArrayList<String[]> revList = test.getReviewList(link);
		
		
//		System.out.println("RevList :" + revList.size());

		productLinks = ScraperTools.getProductLinks(link);
		 	
		for(Map.Entry e : productLinks.entrySet()){
			String tempItem = (String) e.getKey();
			String tempLink = (String) e.getValue();
			
			System.out.println("Hole " + tempLink);
			ArrayList<String[]> revList = null;
			try {
				revList = ScraperTools.getReviewList(tempLink);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			System.out.println("Listengröße: "  + revList.size());
//			System.out.println("+++++++++++++++++++");
			
			ReviewData iData = new ReviewData();
			System.out.println(e.getValue());
			
			
			
			try {
				for(String[] eintrag : revList) {
					
					iData.setId(counter);
					iData.setItemName(tempItem);
					iData.setItemUrl(tempLink);
					iData.setAuthor(eintrag[2]);
					iData.setDate(eintrag[1]);
					iData.setRating(eintrag[3]);
					iData.setText(eintrag[4]);
					iData.setHelpful(eintrag[5]);
					System.out.println("Counter: " + counter);
					System.out.println(iData.getText());
					System.out.println("+++++++++++++++++++++++++++++++++++++++++");
					
					
					exp.exportToDB(iData);
					
					counter++;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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