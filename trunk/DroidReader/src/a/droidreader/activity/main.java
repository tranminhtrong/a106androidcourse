package a.droidreader.activity;

import a.droidreader.R;
import a.droidreader.array_adapter.ChannelListAdapter;
import a.droidreader.controller.connection_controller.DroidReaderNetworkManager;
import a.droidreader.controller.db_controller.DroidReaderOpenHelper;
import a.droidreader.model.Channel;
import a.droidreader.model.Feeds;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import a.droidreader.model.Channel_DAO;

public class main extends Activity implements OnItemClickListener {
	
	public static final int ADD_CHANNEL = Menu.FIRST;

	public static final int ADD_NEW_CHANNEL_CODE = 0;

	public static final int ADD_EDIT_CHANNEL_CODE = 1;

	public static final int CONTEXT_MENU_ADD_CHANNEL = 0;

	public static final int CONTEXT_MENU_CLOSE_MENU = 3;

	public static final int CONTEXT_MENU_DELETE_CHANNEL = 2;

	public static final int CONTEXT_MENU_EDIT_CHANNEL = 1;

	public static final int EDIT_CHANNEL_CODE = 1;

	public static final int RELOAD_CHANNELS = Menu.FIRST + 1;

	ListView channelsListView;

	Feeds feeds;

	private ListView getChannelsListView() {
		if (channelsListView == null) {
			channelsListView = (ListView)findViewById(R.id.channelsListView);
		}
		return channelsListView;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case ADD_NEW_CHANNEL_CODE: {
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					String channelTitleStr = bundle.getString("c_title");
					String channelURLStr = bundle.getString("c_url");
					Channel channel = new Channel();
					channel.setChannelTitle(channelTitleStr);
					channel.setUrl(channelURLStr);
					if (feeds.addChannel(channel)) {
						((ChannelListAdapter)getChannelsListView().getAdapter()).notifyDataSetChanged();
					}
				}
			}
				break;
			case EDIT_CHANNEL_CODE: {
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					String channelIdStr = bundle.getString("c_id");
					String channelTitleStr = bundle.getString("c_title");
					String channelURLStr = bundle.getString("c_url");
					Channel channel = new Channel();
					channel.setChannelId(channelIdStr);
					channel.setChannelTitle(channelTitleStr);
					channel.setUrl(channelURLStr);
					
					if (feeds.updateChannel(channel)) {
						((ChannelListAdapter)getChannelsListView().getAdapter()).notifyDataSetChanged();
					}
				}
			}
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case CONTEXT_MENU_DELETE_CHANNEL: {
				AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
				Channel channel = feeds.getChannels().get(menuInfo.position);
				Channel_DAO channel_DAO = new Channel_DAO(this);
				if (channel_DAO.deleteChannel(channel)) {
					feeds.getChannels().remove(channel);
					((ChannelListAdapter)getChannelsListView().getAdapter()).notifyDataSetChanged();
				}
			}
				break;
			case CONTEXT_MENU_EDIT_CHANNEL: {
				Intent intent = new Intent(this, ChannelComposerActivity.class);
				AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
				Channel channel = feeds.getChannels().get(menuInfo.position);
				Bundle bundle = new Bundle();
				bundle.putString("c_id", channel.getChannelId());
				bundle.putString("c_title", channel.getChannelTitle());
				bundle.putString("c_url", channel.getUrl());
				intent.putExtras(bundle);
				startActivityForResult(intent, EDIT_CHANNEL_CODE);
			}
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channels_manager_activity);
		feeds = new Feeds(this);
		ChannelListAdapter adapter = new ChannelListAdapter(this, feeds.getChannels());
		getChannelsListView().setAdapter(adapter);
		getChannelsListView().setOnItemClickListener(this);
		registerForContextMenu(getChannelsListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		populateMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplication()).inflate(R.menu.channels_manager_activity_menu, menu);
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Channel channel = feeds.getChannels().get(arg2);
		Intent intent = new Intent(this, FeedItemsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("c_title", channel.getChannelTitle());
		bundle.putString("c_url", channel.getUrl());
		intent.putExtras(bundle);
		startActivity(intent);
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
	
	
	
}
