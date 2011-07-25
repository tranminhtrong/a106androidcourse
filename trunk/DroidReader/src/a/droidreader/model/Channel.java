package a.droidreader.model;

import java.util.ArrayList;
import java.util.Date;
import a.droidreader.controller.connection_controller.DroidReaderNetworkManager;
import a.droidreader.controller.xml_parser_controller.*;

public class Channel {
	private String channelId;

	private String channelTitle;

	private String copyright;

	private String description;

	private String generator;

	private ArrayList<FeedItem> items;

	private Date lastBuildDate;

	private Date pubDate;

	private String url;

	public Channel() {
		super();
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public void addFeedItem(FeedItem feedItem) {
		this.items.add(feedItem);
	}

	public void setItems(ArrayList<FeedItem> items) {
		this.items = items;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<FeedItem> getItems() {
		if (items == null) {
			DroidReaderNetworkManager networkManager = new DroidReaderNetworkManager();
			String result = null;
			try {
				result = networkManager.executeHttpGet(this.url);
				FeedSAXParserController xmlPaserController = new FeedSAXParserController(result);
				Channel channel = xmlPaserController.parse();
				this.description = channel.description;
				this.copyright = channel.copyright;
				this.generator = channel.generator;
				this.pubDate = channel.pubDate;
				this.lastBuildDate = channel.lastBuildDate;
				this.items = channel.items;
			} catch (Exception e) {
				e.printStackTrace();
				this.items = new ArrayList<FeedItem>();
			}
		}
		return items;
	}
}
