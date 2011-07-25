package niit.android.array_adapter;

import niit.android.R;
import niit.android.model.Channel;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChannelListAdapter extends ArrayAdapter<Channel> {

	Activity activity;
	Channel[] channels;
	
	public ChannelListAdapter(Activity activity,Channel[] channels) {
		super(activity, 0, channels);
		this.activity = activity;
		this.channels = channels;
	}
	
	public View getView(int position, View convertView,ViewGroup parent) {
		
		Channel channel = channels[position];
		
		View row=convertView;
		 
	    if (row==null) {
	    	LayoutInflater inflater = activity.getLayoutInflater();
	    	row=inflater.inflate(R.layout.channel_item_row, null);
	    }
	 
	    TextView titleTextView = (TextView)row.findViewById(R.id.channelTitleTextView);
		TextView urlTextView = (TextView)row.findViewById(R.id.channelURLTextView);
		
		titleTextView.setText(channel.channelTitle);
		urlTextView.setText(channel.url);
	     
	    return row;
	}

}
