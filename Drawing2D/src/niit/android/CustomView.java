package niit.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View{
	
	private int count = 30;
	
	public int x=0;
	public int y=0;
	private	int	Vx=1,Vy=1;
	private int diameter = 30;
	
	Handler handler = new Handler();
	TimeCountThread timeCountThread = new TimeCountThread();
	DrawBallThread drawThread = new DrawBallThread();
	
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.custom_view);
		String x = a.getString(R.styleable.custom_view_x);
		String y = a.getString(R.styleable.custom_view_y);
		//this.x = Integer.valueOf(x);
		//this.y = Integer.valueOf(y);
		timeCountThread.start();
	}
	
	public CustomView(Context context){
		super(context);
		timeCountThread.start();
	}
	
	public void batDauDiChuyenBanh(){
		//drawThread.start();
		//timeCountThread.start();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		if(count>0)
		{
			
			Paint tPaint = new Paint();
	        tPaint.setColor(Color.RED);
	        
	        canvas.drawText("Time: " + count, 5, 20, tPaint);
	        
	        Paint bPaint = new Paint();
		    
			Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ball);
			canvas.drawBitmap(bitmap, x, y,bPaint );
			
			diChuyenBanh();
			
		} else {
			Paint tPaint = new Paint();
	        tPaint.setColor(Color.RED);
	        
	        canvas.drawText("Bạn đã thua rồi",30,30,tPaint);
		}
	    
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			break;

		default:
			break;
		}
		
		return true;
	}

	protected void diChuyenBanh() {
		
		x=x+Vx;
		y=y+Vy;
		
		if(x<=0||(x>=getWidth()-diameter))
		{
			Vx=-Vx;
		}
		if(y<=0||y>=getHeight()-diameter)
		{
			Vy =-Vy;
		}
		invalidate();
		/*
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				CustomView.this.invalidate();
			}
		});
		*/
	}
	
	private class TimeCountThread extends Thread{

		@Override
		public void run() {
			while(count>0){
				try {
					sleep(1000);
					count--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class DrawBallThread extends Thread{

		@Override
		public void run() {
			while(true){
				try {
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				diChuyenBanh();
			}
		}
		
	}
    	
}
