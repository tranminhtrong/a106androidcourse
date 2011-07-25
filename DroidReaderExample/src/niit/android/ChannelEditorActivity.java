package niit.android;

import niit.android.model.Channel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChannelEditorActivity extends Activity{

	Channel channel = new Channel();
	boolean isEdit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Doc du lieu tu Intent
		
		// Xac dinh yeu cau la Edit hay Add new
		
		
	}
	
	public void onSaveBtnClick(View v){
		
		// Tien hanh save hoac update xuong database
		
		// Tra ve cho Activity truoc.
		
		Intent resultIntent = new Intent();
		
		Bundle bundle = new Bundle();
		bundle.putString("channel_id", channel.channelId);
		bundle.putString("channel_id", channel.channelTitle);
		bundle.putString("channel_ur", channel.url);
		
		this.setResult(RESULT_OK, resultIntent);
		this.finish();
		
	}
	
	public void onCancelBtnClick(View v){
		
		Intent resultIntent = new Intent();
		this.setResult(RESULT_CANCELED, resultIntent);
		this.finish();
		
	}
	
}
