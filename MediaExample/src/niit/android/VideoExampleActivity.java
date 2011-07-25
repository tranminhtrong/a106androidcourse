package niit.android;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoExampleActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.video_example);
		VideoView videoView = (VideoView)this.findViewById(R.id.videoView);
		MediaController mc = new MediaController(this);
		videoView.setMediaController(mc);
		//videoView.setVideoURI(Uri.parse("http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/movie.mp4"));
		videoView.setVideoPath("sdcard/Wildfire.wmv");
		videoView.requestFocus();
		videoView.start();
	}
}
