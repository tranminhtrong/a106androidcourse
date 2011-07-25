package a.droidreader.model;

import java.util.Date;

public class FeedItem {

	private	String 	title;
	private	String	description;
	private	String	link;
	private	Date	pubDate;
	private boolean unRead = true;

	public boolean isUnRead() {
		return unRead;
	}

	public void setUnRead(boolean unRead) {
		this.unRead = unRead;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
}
