package a.droidreader.model;

import java.util.ArrayList;
import android.content.Context;

public class Feeds {
	
	private	ArrayList<Channel> channels;
	private Context context;
	
	public Feeds(Context context){
		this.context = context;
	}
	
	public ArrayList<Channel> getChannels(){
		if(channels==null){
			channels = readChannelsFromDatabase();
		}
		return channels;
	}
	
	public boolean updateChannel(Channel channel){
		
		Channel_DAO channel_DAO = new Channel_DAO(context);
		
		boolean flag = channel_DAO.updateChannel(channel);
		channel_DAO = null;
		
		return	flag;
		
	}
	
	public boolean addChannel(Channel channel){
		
		Channel_DAO channel_DAO = new Channel_DAO(context);
		boolean flag = false;
		
		flag = channel_DAO.saveChannel(channel);
		
		if(flag)
			channels.add(channel);
		
		channel_DAO = null;
		
		return flag;
	}
	
	private ArrayList<Channel> readChannelsFromDatabase(){
		
		Channel_DAO channel_DAO = new Channel_DAO(context);
		
		Channel[] tmp = channel_DAO.getAllChannel();
		channel_DAO = null;
		
		ArrayList<Channel> channels = new ArrayList<Channel>();
		
		for(int i=0;i<tmp.length;i++)
		{
			channels.add(tmp[i]);
		}
		
		tmp = null;
		
		return channels;
	}
	
}
