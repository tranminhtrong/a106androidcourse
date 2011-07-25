package a.droidreader.array_adapter;

import java.util.ArrayList;
import a.droidreader.R;
import a.droidreader.model.FeedItem;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FeedItemListArrayAdapter extends ArrayAdapter<FeedItem> {
	private class RowWrapper {
		View base;

		TextView pubDateLabel = null;

		TextView titleLabel = null;

		RowWrapper(View base) {
			this.base = base;
		}

		TextView getPubDateLabel() {
			if (pubDateLabel == null) {
				pubDateLabel = (TextView)base.findViewById(R.id.feedItemPudDateLabel);
			}
			return (pubDateLabel);
		}

		TextView getTitleLabel() {
			if (titleLabel == null) {
				titleLabel = (TextView)base.findViewById(R.id.feedItemTitleLabel);
			}
			return (titleLabel);
		}
	}

	Activity act;

	ArrayList<FeedItem> items;

	public FeedItemListArrayAdapter(Activity act, ArrayList<FeedItem> items) {
		super(act, R.layout.feed_item_row, items);
		this.act = act;
		this.items = items;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RowWrapper wrapper = null;
		if (row == null) {
			LayoutInflater inflater = act.getLayoutInflater();
			row = inflater.inflate(R.layout.feed_item_row, null);
			wrapper = new RowWrapper(row);
			row.setTag(wrapper);
		} else {
			wrapper = (RowWrapper)row.getTag();
		}
		FeedItem feedItem = items.get(position);
		wrapper.getTitleLabel().setText(feedItem.getTitle());
		wrapper.getPubDateLabel().setText(feedItem.getPubDate().toGMTString());
		if (!feedItem.isUnRead())
			row.setBackgroundColor(Color.argb(60, 200, 200, 200));
		else
			row.setBackgroundColor(Color.argb(100, 0, 0, 0));
		return (row);
	}
}
