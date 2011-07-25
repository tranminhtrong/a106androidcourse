package niit.android;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ViewThread extends Thread {
    private GamePanel mPanel;
    private SurfaceHolder mHolder;
    private boolean mRun = false;
    private long mStartTime;
    private long mElapsed;
    
    public ViewThread(GamePanel panel) {
        mPanel = panel;
        mHolder = mPanel.getHolder();
    }
    
    public void setRunning(boolean run) {
        mRun = run;
    }
    
    @Override
    public void run() {
        Canvas canvas = null;
        mStartTime = System.currentTimeMillis();
        while (mRun) {
            canvas = mHolder.lockCanvas();
            if (canvas != null) {
                mPanel.changeGamePanel();
                mPanel.drawGamePanel(canvas);
                mElapsed = System.currentTimeMillis() - mStartTime;
                mHolder.unlockCanvasAndPost(canvas);
            }
            mStartTime = System.currentTimeMillis();
        }
    }
}