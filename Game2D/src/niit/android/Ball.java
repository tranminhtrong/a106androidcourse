package niit.android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Ball {
	// Hai thuoc tinh chieu rong va chieu cao cua GamePanel.
	private float gamePanelWidth = 0;
	private float gamePanelHeight = 0;
	
	// Hai thuoc tinh xac dinh toa do (x,y) hien tai cua trai banh.
    private float x;
    private float y;
    
    // Duong kinh trai banh
	private float diameter;
    
	// Van toc theo phuong ngang va theo phuong doc cua trai banh.
    private float Vx;
    private float Vy;
    
    // Doi tuong Bitmap chua hinh anh trai banh.
    private Bitmap bitmap;

    // Hai phuong thuc set, get cua thuoc tinh Bitmap.
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap mBitmap) {
		this.bitmap = mBitmap;
	}
    
	// Phuong thuc gan toa do cho trai banh.
    public void setPosition(float x, float y){
    	x = x;
    	y = y;
    }
    
    // Phuong thuc gan van toc cho trai banh.
    public void setVelosity(float Vx, float Vy){
    	this.Vx = Vx;
    	this.Vy = Vy;
    }
    
    // Phuong thuc khoi tao trai banh.
    public Ball(Resources res, float x, float y, float gamePanelWidth,float gamePanelHeight) {
        bitmap = BitmapFactory.decodeResource(res, R.drawable.ball);
        this.x = x;
        this.y = y;
        diameter = bitmap.getWidth();
        
        Vx = 0;
        Vy = 0;
        
        this.gamePanelHeight = gamePanelHeight;
        this.gamePanelWidth = gamePanelWidth;
        
    }
    
    // Phuong thuc ve trai banh bang doi tuong Canvas duoc truyen vao duoi dang tham so.
    public void drawBall(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
    
    // Phuong thuc di chuyen trai banh.
    public void moveBall() {
        x += Vx;
        y += Vy;
        checkBallPosition();
    }

    // Phuong thuc kiem tra laij vi tri trai banh, neu vuot qua bien ngang hoac bien doc thi doi huong trai banh lai.
    private void checkBallPosition() {
		if(x<=0||(x>=gamePanelWidth-diameter))
		{
			Vx=-Vx;
		}
		if(y<=0||y>=gamePanelHeight-diameter)
		{
			Vy =-Vy;
		}
    }
	
}
