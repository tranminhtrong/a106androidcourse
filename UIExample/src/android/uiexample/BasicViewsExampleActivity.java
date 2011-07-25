package android.uiexample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
 
public class BasicViewsExampleActivity  extends Activity 
{
	
	private static int progress = 0;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_views);
 
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
 
        //---do some work in background thread---
        Thread updateProcessBarThread = new Thread(new Runnable() 
        {
        	public void run() 
            {
                while (progressStatus < 100) 
                {
                    progressStatus = doSomeWork();
 
                    //---Update the progress bar---
                    handler.post(new Runnable() 
                    {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
 
                }
                //---hides the progress bar---
                handler.post(new Runnable() 
                {
                    public void run() {
                        //---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                        progressBar.setVisibility(8);
                    }
                });
            }    
 
            //---do some long lasting work here---
            private int doSomeWork() 
            {
                try {
                    //---simulate doing some work---
                    Thread.sleep(500);
                } catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
                return ++progress;
            }
 
        });
        
        updateProcessBarThread.start();
    }
}
