package niit.android.model;

import android.content.Context;

public class Feeds {
	
	public	String rssVersion;
	public	Channel[] channels;
	private Context context;
	
	public Feeds(Context context){
		this.context = context;
	}
	
	public Channel[] getChannels(){
		
		if(channels==null){
			channels = readChannelsFromDatabase();
		}
		
		return channels;
	
	}
	
	public boolean addChannel(Channel channel){
		
		boolean flag = false;
		flag = channel.saveChannel(context);
		
		return flag;
		
	}
	
	private Channel[] readChannelsFromDatabase(){
		
		Channel[] tmp = null;
		try{
			tmp = Channel.getAllChannel(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return tmp;
	}
	
}
