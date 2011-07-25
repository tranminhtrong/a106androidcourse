package a.droidreader.array_adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import a.droidreader.R;
import a.droidreader.model.*;

public class ChannelListAdapter extends ArrayAdapter<Channel> {

	Activity activity;
	ArrayList<Channel> channels;
	
	public ChannelListAdapter(Activity activity,ArrayList<Channel> channels) {
		super(activity, 0, channels);
		this.activity = activity;
		this.channels = channels;
	}
	
	public View getView(int position, View convertView,ViewGroup parent) {
		
		Channel channel = channels.get(position);
		
		View row=convertView;
		RowWrapper wrapper=null;
		 
	    if (row==null) {
	    	LayoutInflater inflater = activity.getLayoutInflater();
	        
	    	row=inflater.inflate(R.layout.channel_item_row, null);
	    	wrapper=new RowWrapper(row);
	    	row.setTag(wrapper);
	    }
	    else {
	    	wrapper=(RowWrapper)row.getTag();
	    }
	 
	    wrapper.getTitleLabel().setText(channel.getChannelTitle());
	    wrapper.getURLLabel().setText(channel.getUrl());
	     
	    return row;
	}
	
	private class RowWrapper {
		
		View base;
		TextView titleTextView = null;
		TextView urlTextView = null;
		
		RowWrapper(View base) {
			this.base=base;
		}
		
		TextView getTitleLabel() {
			if (titleTextView==null) {
				titleTextView=(TextView)base.findViewById(R.id.channelTitleTextView);
			}
		  return(titleTextView);
		}
		
		TextView getURLLabel() {
			if (urlTextView==null) {
				urlTextView=(TextView)base.findViewById(R.id.channelURLTextView);
			}
		  return(urlTextView);
		}
	}

}
