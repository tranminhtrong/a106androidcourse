package niit.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundService extends Service{
	
	private NotificationManager notificationMgr; 
	private	ServiceThread serviceThread;
	
	@Override 
    public void onCreate() { 
		super.onCreate(); 
 
        notificationMgr =(NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
 
        displayNotificationMessage("Starting Background Service"); 
 
        serviceThread = new ServiceThread();
        serviceThread.start();
 
    }
    @Override 
    public void onStart(Intent intent, int startId) { 
        super.onStart(intent, startId); 
 
    }
    
    @Override 
    public IBinder onBind(Intent intent) { 
        return null; 
    } 
 
    @Override 
    public void onDestroy() 
    { 
        displayNotificationMessage("Stopping Background Service"); 
        super.onDestroy(); 
 
    } 
 
    private void displayNotificationMessage(String message) 
    {
        Notification notification = new Notification(R.drawable.icon,message,System.currentTimeMillis()); 
 
        PendingIntent contentIntent =  PendingIntent.getActivity(this, 0, new Intent(this, main.class), 0); 
 
        notification.setLatestEventInfo(this, "Background Service",message,contentIntent);
        notificationMgr.notify(123456, notification);
    }
	
    class ServiceThread extends Thread 
    {
		@Override
		public void run() {
			// Thực thi các tác vụ mong muốn.
		}
    } 
}
