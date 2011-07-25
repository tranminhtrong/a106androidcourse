package niit.android.controller.xml_parser_controller;

import java.util.ArrayList;
import java.util.Date;
import niit.android.model.Channel;
import niit.android.model.FeedItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FeedSAXHandler extends DefaultHandler {
	
	static private int	channelKey = 0;
	static private int	itemKey = 1;
	
	private Channel channel;
    private FeedItem currentFeedItem;
    private StringBuilder builder;
    private int currentElement;
    
    public Channel getChannel(){
        return this.channel;
    }
    
    @Override
    public void characters(char[] ch, int start, int length)	throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)	throws SAXException {
    	
        super.endElement(uri, localName, name);
        
        if (localName.equalsIgnoreCase("item")){
            
        	channel.items.add(currentFeedItem);
        
        } else if(currentElement==channelKey){
    		
    		if (localName.equalsIgnoreCase("title")){
            	
            	channel.channelTitle = builder.toString();
            
            } else if (localName.equalsIgnoreCase("link")){
            
            	channel.url = builder.toString();
            
            } else if (localName.equalsIgnoreCase("description")){
            
            	channel.description = builder.toString();
            
            } else if (localName.equalsIgnoreCase("pubDate")){
            	
            	channel.pubDate = new Date(builder.toString());
            	
            }
    		
    	} else if(currentElement==itemKey){
    		
    		if (localName.equalsIgnoreCase("title")){
            	
            	currentFeedItem.title = builder.toString();
            
            } else if (localName.equalsIgnoreCase("link")){
            
            	currentFeedItem.link = builder.toString();
            
            } else if (localName.equalsIgnoreCase("description")){
            	
            	String description = builder.toString();
            	
            	currentFeedItem.description = description;
            	
            } else if (localName.equalsIgnoreCase("pubDate")){
            
            	currentFeedItem.pubDate = new Date(builder.toString());
            
            }
    	}
        
        builder.setLength(0);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        channel = new Channel();
        channel.items = new ArrayList<FeedItem>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        
        if (localName.equalsIgnoreCase("channel")){
        	currentElement = channelKey;
        } else if (localName.equalsIgnoreCase("item")){
            this.currentFeedItem = new FeedItem();
            currentElement = itemKey;
        }
        
    }
}
