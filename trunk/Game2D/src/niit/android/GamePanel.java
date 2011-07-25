package niit.android;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    private float lastX = 0;
    private float lastY = 0;
    
    private GameThread gameThread = new GameThread();
    private int mBallNumber = 0;
    
    private Ball newBall;
    
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    
    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    
    
    
    public GamePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
        
	}
    
	public void drawGamePanel(Canvas canvas) {
    	
		canvas.drawColor(Color.BLACK);
    	/*
    	synchronized (ball) {
			ball.drawBall(canvas);
		}
    	*/
		synchronized (balls) {
        	for (Ball ball : balls) {
        		ball.drawBall(canvas);
			}
        }
    }
	
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        
        if (!gameThread.isAlive()) {
            gameThread = new GameThread();
            gameThread.setRunning(true);
            gameThread.start();
        }
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (gameThread.isAlive()) {
            gameThread.setRunning(false);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	switch (event.getAction()) {
    	
			case MotionEvent.ACTION_DOWN:
			{
				lastX = event.getX();
				lastY = event.getY();
				
				synchronized (balls) {
					newBall = new Ball(getResources(),lastX,lastY, 0, 0);
					
					newBall.setPosition(lastX, lastY);
					newBall.setVelosity(0,0);
					balls.add(newBall);
				}
			}
			return true;
			
			case MotionEvent.ACTION_MOVE:
			{
				synchronized (newBall) {
					newBall.setPosition(event.getX(), event.getY());
				}
			}
			return true;
			
			case MotionEvent.ACTION_UP:
			{
				if(newBall!=null)
				{
					synchronized (newBall) {
						newBall.setVelosity((float)((event.getX()-lastX)/10.0),(float)((event.getY()-lastY)/10.0));
					}
				}
			}
			return true;

			default:
				
			break;
		}
    	
        return super.onTouchEvent(event);
    }
	
    public void changeGamePanel() {
    	
        synchronized (balls) {
        	for (Ball ball : balls) {
				ball.moveBall();
			}
        }
        
		/*
    	
    	synchronized (ball) {
    		ball.moveBall();
		}
    	*/
    }
    
    class GameThread extends Thread {
        private SurfaceHolder mHolder;
        private boolean mRun = false;
        
        public GameThread() {
            mHolder = GamePanel.this.getHolder();
        }
        
        public void setRunning(boolean run) {
            mRun = run;
        }
        
        @Override
        public void run() {
            Canvas canvas = null;
            while (mRun) {
                canvas = mHolder.lockCanvas();
                if (canvas != null) {
                	GamePanel.this.changeGamePanel();
                	GamePanel.this.drawGamePanel(canvas);
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    
}
