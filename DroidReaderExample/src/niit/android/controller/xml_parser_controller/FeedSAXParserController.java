package niit.android.controller.xml_parser_controller;

import javax.xml.parsers.SAXParserFactory;
import niit.android.model.Channel;
import android.util.Xml;

public class FeedSAXParserController {

	private String xmlData;

	public FeedSAXParserController(String xmlData){
        super();
        this.xmlData = xmlData;
    }
    
    public Channel parse() {
    	
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
        	FeedSAXHandler handler = new FeedSAXHandler();
            Xml.parse(xmlData, handler);
            return handler.getChannel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
	
}
