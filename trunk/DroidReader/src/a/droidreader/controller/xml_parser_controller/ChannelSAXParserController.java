package a.droidreader.controller.xml_parser_controller;

import a.droidreader.model.Channel;
import android.util.Xml;

public class ChannelSAXParserController {
	private String xmlData;

	public ChannelSAXParserController(String xmlData) {
		super();
		this.xmlData = xmlData;
	}

	public Channel parse() {
		try {
			FeedSAXHandler handler = new FeedSAXHandler();
			Xml.parse(xmlData, handler);
			return handler.getChannel();
		} catch (Exception e) {
		}
		return null;
	}
}
