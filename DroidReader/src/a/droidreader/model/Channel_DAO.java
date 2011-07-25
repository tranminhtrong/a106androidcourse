package a.droidreader.model;

import a.droidreader.controller.db_controller.DroidReaderOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Channel_DAO {
	
	private Context context;
	
	public Channel_DAO(Context context) {
		super();
		this.context = context;
	}

	public Channel[] getAllChannel(){
		
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
				
				tmp.setChannelId(cursor.getString(0));
				tmp.setChannelTitle(cursor.getString(1));
				tmp.setUrl(cursor.getString(2));
				i++;
				cursor.moveToNext();
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
	
	public	Channel getChannelById(String id){
		
		Channel channel = null;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		String[] colums = {"c_id","c_tile","c_url"};
		String[] selections = {id};
		try {
			Cursor cursor = null;
			cursor = database.query("channel", colums , "id=?", selections, null, null, null);
			channel = new Channel();
			cursor.moveToFirst();
			channel.setChannelId(cursor.getString(0));
			channel.setChannelTitle(cursor.getString(1));
			channel.setUrl(cursor.getString(2));
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
	
	public boolean deleteChannel(Channel channel){
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		
		String paramas[] = new String[1];
		paramas[0] = channel.getChannelId();
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
	
	public boolean saveChannel(Channel channel){
		
		long id = -1;
		boolean flag = true;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put("c_title", channel.getChannelTitle());
		cv.put("c_url", channel.getUrl());
		
		try{
			id = database.insert("channel",null,cv);
			channel.setChannelId(String.valueOf(id));
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
	
	public boolean updateChannel(Channel channel){
		
		int num = 0;
		
		DroidReaderOpenHelper openHelper = new DroidReaderOpenHelper(context,1);
		SQLiteDatabase database = openHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("c_title", channel.getChannelTitle());
		values.put("c_url", channel.getUrl());
		
		String[] params = new String[]{channel.getChannelId()};
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
	
}
