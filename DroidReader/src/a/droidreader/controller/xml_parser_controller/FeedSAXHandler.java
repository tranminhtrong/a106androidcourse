package a.droidreader.controller.xml_parser_controller;

import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import a.droidreader.model.Channel;
import a.droidreader.model.FeedItem;

public class FeedSAXHandler extends DefaultHandler {
	
	static private int	channelKey = 0;
	static private int	itemKey = 1;
	
	private StringBuilder builder;
    private Channel channel;
    private int currentElement = -1;
    private FeedItem currentFeedItem;
    
    public Channel getChannel(){
        return this.channel;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        channel = new Channel();
        channel.setItems(new ArrayList<FeedItem>());
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
    
    @Override
    public void endElement(String uri, String localName, String name)	throws SAXException {
    	
        super.endElement(uri, localName, name);
        
        if (localName.equalsIgnoreCase("item")){
            
        	channel.addFeedItem(currentFeedItem);
        
        } else if(currentElement==channelKey){
    		
    		if (localName.equalsIgnoreCase("title")){
            	
            	channel.setChannelTitle(builder.toString());
            
            } else if (localName.equalsIgnoreCase("link")){
            
            	channel.setUrl(builder.toString());
            
            } else if (localName.equalsIgnoreCase("description")){
            
            	channel.setDescription(builder.toString());
            
            } else if (localName.equalsIgnoreCase("pubDate")){
            	
            	channel.setPubDate(new Date(builder.toString()));
            	
            }
    		
    	} else if(currentElement==itemKey){
    		
    		if (localName.equalsIgnoreCase("title")){
            	
            	currentFeedItem.setTitle(builder.toString());
            
            } else if (localName.equalsIgnoreCase("link")){
            
            	currentFeedItem.setLink(builder.toString());
            
            } else if (localName.equalsIgnoreCase("description")){
            	
            	String description = builder.toString();
            	
            	currentFeedItem.setDescription(description);
            	
            } else if (localName.equalsIgnoreCase("pubDate")){
            
            	currentFeedItem.setPubDate(new Date(builder.toString()));
            
            }
    	}
    	
        builder.setLength(0);
    }
    
    @Override
    public void characters(char[] ch, int start, int length)	throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }
}
