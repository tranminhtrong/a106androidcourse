package niit.android;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioExampleActivity extends Activity {
	//static final String AUDIO_PATH = "http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/play.mp3";
	static final String AUDIO_PATH = "/sdcard/music_file.mp3";
	private MediaPlayer mediaPlayer;

	private int playbackPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_example);
		Button btnStartPlayer = (Button)findViewById(R.id.btnStartPlayer);
		Button btnPausePlayer = (Button)findViewById(R.id.btnPausePlayer);
		Button btnResumePlayer = (Button)findViewById(R.id.btnResumePlayer);
		btnStartPlayer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					playAudio(AUDIO_PATH);
					//playLocalAudio();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnPausePlayer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mediaPlayer != null) {
					playbackPosition = mediaPlayer.getCurrentPosition();
					mediaPlayer.pause();
				}
			}
		});
		btnResumePlayer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
					mediaPlayer.seekTo(playbackPosition);
					mediaPlayer.start();
				}
			}
		});
	}

	private void playLocalAudio_UsingDescriptor() throws Exception {	 
		AssetFileDescriptor fileDesc = getResources().openRawResourceFd(R.raw.music_file);
		if (fileDesc != null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
			fileDesc.close();
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
	}

	private void playLocalAudio() throws Exception {
		mediaPlayer = MediaPlayer.create(this, R.raw.music_file);
		mediaPlayer.start();
	}

	private void playAudio(String url) throws Exception {
		killMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepare();
		mediaPlayer.start();
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
	protected void onDestroy() {
		super.onDestroy();
		killMediaPlayer();
	}
}
