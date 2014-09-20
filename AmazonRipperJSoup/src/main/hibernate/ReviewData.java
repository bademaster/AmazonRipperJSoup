package main.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity 
@Table (name="ITEM_DATA")
public class ReviewData {
	/** The Item ID */
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="ITEM_ID")
	private int id;
	@Column (name="ITEM_NAME")
	private String itemName;
	@Column (name="ITEM_URL")
	private String itemUrl;
	@Column (name="REV_AUTHOR")
	private String author;
	@Column (name="REV_DATE")
	private String date;
	@Column (name="REV_RATING")
	private String rating;
	@Column (columnDefinition="LONGTEXT", name="REV_TEXT")
	private String text;
	@Column (name="REV_HELPFUL")
	private String helpful;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHelpful() {
		return helpful;
	}
	public void setHelpful(String helpful) {
		this.helpful = helpful;
	}
	

	

}
