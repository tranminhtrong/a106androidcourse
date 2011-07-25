package niit.android.model;

import java.util.ArrayList;
import java.util.Date;
import niit.android.controller.connection_controller.DroidReaderNetworkManager;
import niit.android.controller.db_controller.DroidReaderOpenHelper;
import niit.android.controller.xml_parser_controller.FeedSAXParserController;
import niit.android.model.Channel;
import niit.android.model.FeedItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Channel{
	
	public	String 	channelId;
	public	String 	channelTitle;
	public	String 	url;
	public	String	description;
	public	String 	copyright;
	public	String	generator;
	public	Date	pubDate;
	public	Date	lastBuildDate;
	public	ArrayList<FeedItem>	items;
	
	public Channel(){
		super();
	}
	
	public static	Channel getChannelById(Context context, String id){
		
		Channel channel = null;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		int num = 0;
		String[] colums = {"c_id","c_tile","c_url"};
		String[] selections = {id};
		try {
			Cursor cursor = null;
			cursor = database.query("channel", colums , "id=?", selections, null, null, null);
			channel = new Channel();
			cursor.moveToFirst();
			channel.channelId = cursor.getString(0);
			channel.channelTitle = cursor.getString(1);
			channel.url = cursor.getString(2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				database.close();
				openHelper.close();
				database = null;
				openHelper = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return channel;
	}
	
	public static	Channel[] getAllChannel(Context context){
		
		Channel[] channels = null;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		String[] colums = {"c_id","c_title","c_url"};
		try {
			Cursor cursor = null;
			cursor = database.query("channel", colums , null, null, null, null, null);
			channels = new Channel[cursor.getCount()];
			cursor.moveToFirst();
			int i=0;
			while(!cursor.isAfterLast()){
				
				Channel tmp = new Channel();
				channels[i] = tmp;
				
				tmp.channelId = cursor.getString(0);
				tmp.channelTitle = cursor.getString(1);
				tmp.url = cursor.getString(2);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				database.close();
				openHelper.close();
				database = null;
				openHelper = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return channels;
	}
	
	public boolean deleteChannel(Context context){
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		
		String paramas[] = new String[1];
		paramas[0] = this.channelId;
		int num = 0;
		try {
			num = database.delete("channel", "c_id=?", paramas);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				database.close();
				openHelper.close();
				database = null;
				openHelper = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return num>0;
		
	}
	
	public boolean updateChannel(Context context){
		
		int num = 0;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("c_title", this.channelTitle);
		values.put("c_url", this.url);
		
		String[] params = new String[]{this.channelId};
		try {
			num = database.update("channel", values, "c_id=?", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				database.close();
				openHelper.close();
				database = null;
				openHelper = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return num>0;
		
	}
	
	public boolean saveChannel(Context context){
		
		long id = -1;
		boolean flag = true;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put("c_title", this.channelTitle);
		cv.put("c_url", this.url);
		
		try{
			id = database.insert("channel",null,cv);
			this.channelId = String.valueOf(id);
		}
		catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		finally{
			try {
				database.close();
				openHelper.close();
				database = null;
				openHelper = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return flag;
		
	}
	
	public ArrayList<FeedItem> getItems() {
		if (items == null) {
			DroidReaderNetworkManager networkManager = new DroidReaderNetworkManager();
			String result = null;
			try {
				result = networkManager.executeHttpGet(this.url);
				FeedSAXParserController xmlPaserController = new FeedSAXParserController(result);
				Channel channel = xmlPaserController.parse();
				this.description = channel.description;
				this.copyright = channel.copyright;
				this.generator = channel.generator;
				this.pubDate = channel.pubDate;
				this.lastBuildDate = channel.lastBuildDate;
				this.items = channel.items;
			} catch (Exception e) {
				e.printStackTrace();
				this.items = new ArrayList<FeedItem>();
			}
		}
		return items;
	}
	
}
