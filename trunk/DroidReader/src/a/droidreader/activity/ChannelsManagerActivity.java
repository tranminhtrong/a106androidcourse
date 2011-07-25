package a.droidreader.activity;

import a.droidreader.R;
import a.droidreader.array_adapter.ChannelListAdapter;
import a.droidreader.model.Channel;
import a.droidreader.model.Channel_DAO;
import a.droidreader.model.Feeds;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class ChannelsManagerActivity extends Activity {
	ListView channelsListView;

	Feeds feeds;

	public static final int ADD_NEW_CHANNEL_CODE = 0;

	public static final int CONTEXT_MENU_ADD_CHANNEL = 0;

	public static final int CONTEXT_MENU_EDIT_CHANNEL = 1;

	public static final int CONTEXT_MENU_DELETE_CHANNEL = 1;

	public static final int CONTEXT_MENU_CLOSE_MENU = 2;

	public static final int ADD_CHANNEL = Menu.FIRST;

	public static final int RELOAD_CHANNELS = Menu.FIRST + 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		feeds = new Feeds(this);
		setContentView(R.layout.channels_manager_activity);
		ChannelListAdapter adapter = new ChannelListAdapter(this, feeds.getChannels());
		getChannelsListView().setAdapter(adapter);
		registerForContextMenu(getChannelsListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		populateMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplication()).inflate(R.menu.channels_manager_activity_menu, menu);
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case R.id.addChannelMenuItemId: {
				Intent intent = new Intent(this, ChannelComposerActivity.class);
				startActivityForResult(intent, ADD_NEW_CHANNEL_CODE);
			}
				break;
			default:
				break;
		}
		return true;
	}

	private void populateMenu(Menu menu) {
		menu.add(Menu.NONE, CONTEXT_MENU_ADD_CHANNEL, 0, R.string.context_menu_title__add_new_channel);
		menu.add(Menu.NONE, CONTEXT_MENU_EDIT_CHANNEL, 0, R.string.context_menu_title__edit_channel);
		menu.add(Menu.NONE, CONTEXT_MENU_DELETE_CHANNEL, 0, R.string.context_menu_title__delete_channel);
		menu.add(Menu.NONE, CONTEXT_MENU_CLOSE_MENU, 1, R.string.context_menu_title__close_menu);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case ADD_NEW_CHANNEL_CODE: {
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					String channelTitleStr = bundle.getString("channel_title");
					String channelURLStr = bundle.getString("channel_url");
					Channel channel = new Channel();
					channel.setChannelTitle(channelTitleStr);
					channel.setUrl(channelURLStr);
					Channel_DAO channel_DAO = new Channel_DAO(this);
					if (channel_DAO.saveChannel(channel)) {
						feeds.getChannels().add(channel);
					}
				}
			}
				break;
				/*
			case ADD_EDIT_CHANNEL_CODE: {
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					String channelTitleStr = bundle.getString("channel_title");
					String channelURLStr = bundle.getString("channel_url");
					Channel channel = new Channel();
					channel.setChannelTitle(channelTitleStr);
					channel.setUrl(channelURLStr);
					Channel_DAO channel_DAO = new Channel_DAO(this);
					if (channel_DAO.saveChannel(channel)) {
						feeds.getChannels().add(channel);
					}
				}
			}
				break;
				*/
			default:
				break;
		}
	}

	// Outlets methods
	private ListView getChannelsListView() {
		if (channelsListView == null) {
			channelsListView = (ListView)findViewById(R.id.channelsListView);
		}
		return channelsListView;
	}
}
