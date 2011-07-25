package niit.android;

import niit.android.array_adapter.ChannelListAdapter;
import niit.android.model.Channel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class main extends Activity implements OnItemClickListener {
	
	public static final int ADD_NEW_CHANNEL_CODE = 0;

	public static final int EDIT_CHANNEL_CODE = 1;

	public static final int CONTEXT_MENU_ADD_CHANNEL = 0;

	public static final int CONTEXT_MENU_CLOSE_MENU = 1;

	public static final int CONTEXT_MENU_DELETE_CHANNEL = 2;

	public static final int CONTEXT_MENU_EDIT_CHANNEL = 3;
	
	ListView channelListView;
	Channel[] channels;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        channels = Channel.getAllChannel(this);
        channelListView = (ListView)findViewById(R.id.channelsListView);
        
        ChannelListAdapter adapter = new ChannelListAdapter(this, channels);
        channelListView.setAdapter(adapter);
        channelListView.setOnItemClickListener(this);
        channelListView.setOnCreateContextMenuListener(this);
        registerForContextMenu(channelListView);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case CONTEXT_MENU_ADD_CHANNEL:
				
				break;
			case CONTEXT_MENU_EDIT_CHANNEL:
				
				AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
				Channel channel = channels[menuInfo.position];
				
				Intent intent = new Intent();
				
				Bundle bundle = new Bundle();
				bundle.putInt("code", CONTEXT_MENU_EDIT_CHANNEL);
				bundle.putString("channel_id", channel.channelId);
				bundle.putString("channel_title", channel.channelTitle);
				bundle.putString("channel_url", channel.url);
				
				intent.putExtras(bundle);
				
				startActivityForResult(intent, EDIT_CHANNEL_CODE);
				
				break;
			case CONTEXT_MENU_DELETE_CHANNEL:
				
				break;
			case CONTEXT_MENU_CLOSE_MENU:
				
				break;
			default:
				break;
		}
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, CONTEXT_MENU_ADD_CHANNEL, 0, "Add new channel");
		menu.add(Menu.NONE, CONTEXT_MENU_EDIT_CHANNEL, 0, "Edit channel");
		menu.add(Menu.NONE, CONTEXT_MENU_DELETE_CHANNEL, 0, "Delete channel");
		menu.add(Menu.NONE, CONTEXT_MENU_CLOSE_MENU, 1, "Close");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		/*
		Channel channel = channels[arg2];
		Intent intent = new Intent(this, FeedItemsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("c_title", channel.channelTitle);
		bundle.putString("c_url", channel.url);
		intent.putExtras(bundle);
		startActivity(intent);
		*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
			case ADD_NEW_CHANNEL_CODE:
				
				break;

			case EDIT_CHANNEL_CODE:
				
				break;
			default:
				break;
		}
		
	}
	
	
}