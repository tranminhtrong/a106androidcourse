package niit.android.array_adapter;

import java.util.ArrayList;
import niit.android.R;
import niit.android.controller.FeedImageGetter;
import niit.android.model.FeedItem;
import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FeedItemListArrayAdapter extends ArrayAdapter<FeedItem> {
	
	Activity act;
	
	ArrayList<FeedItem> items;

	public FeedItemListArrayAdapter(Activity act, ArrayList<FeedItem> arrayList) {
		super(act, R.layout.feed_item_row, arrayList);
		this.act = act;
		this.items = arrayList;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = act.getLayoutInflater();
			row = inflater.inflate(R.layout.feed_item_row, null);
		}
		FeedItem feedItem = items.get(position);
		
		TextView tvFeedItemTitle = (TextView)row.findViewById(R.id.feedItemTitleLabel);
		TextView tvFeedItemPubDate = (TextView)row.findViewById(R.id.feedItemPudDateLabel);
		TextView tvFeedItemDescription = (TextView)row.findViewById(R.id.feedItemDescription);
		
		tvFeedItemTitle.setText(feedItem.title);
		tvFeedItemPubDate.setText(feedItem.pubDate.toGMTString());
		tvFeedItemDescription.setText(Html.fromHtml(feedItem.description, new FeedImageGetter(), null));
		
		return (row);
	}
}
