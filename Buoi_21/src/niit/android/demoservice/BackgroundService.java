package niit.android.demoservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundService extends Service {
	private NotificationManager notificationMgr;

	private MediaPlayer mediaPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		notificationMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		displayNotificationMessage("Starting Background Service");
	}

	private void playLocalAudio_UsingDescriptor() throws Exception {
		AssetFileDescriptor fileDesc = getResources().openRawResourceFd(R.raw.audio);
		if (fileDesc != null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
			fileDesc.close();
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
	}

	private void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		try {
			playLocalAudio_UsingDescriptor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		displayNotificationMessage("Stopping Background Service");
		killMediaPlayer();
		super.onDestroy();
	}

	private void displayNotificationMessage(String message) {
		Notification notification = new Notification(R.drawable.icon, message, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, main.class), 0);
		notification.setLatestEventInfo(this, "Background Service", message, contentIntent);
		notificationMgr.notify(123456, notification);
	}
}
