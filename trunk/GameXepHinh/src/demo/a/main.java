package demo.a;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.jar.Attributes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity implements View.OnClickListener{
	static	final	int picturePuzzleHeight = 4;
	static	final	int picturePuzzleWidth = 4;
	static	final	int	cellWidth = 320/picturePuzzleWidth ;
	static	final	int	cellHeight = 320/picturePuzzleHeight;
    private	ImageButton	picture[][] = new ImageButton[picturePuzzleHeight][picturePuzzleWidth];
	private	AbsoluteLayout mainViewGroup;
    
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        mainViewGroup = (AbsoluteLayout) this.findViewById(R.id.main);
                
        this.batDauChoi(null);
        
        Toast toast = Toast.makeText(this, "Nhấn \"Bắt đầu chơi\" nào :)",Toast.LENGTH_SHORT);
		toast.show();
		
	}
	
	public void batDauChoi(View v){
		
		mainViewGroup.removeAllViews();
		
		InputStream is = getResources().openRawResource( R.raw.walle);
        BitmapDrawable bm = new BitmapDrawable(is);
        try {
            is.close();
        } catch (IOException e) { 
        	
        }
        
        for(int i=0;i<picturePuzzleHeight;i++){
        
        	for(int j=0;j<picturePuzzleWidth;j++){
        		
        		if(picture[i][j]!=null){
        			picture[i][j]=null;        		
        		}
        		
                BitmapDrawable img = new BitmapDrawable( Bitmap.createBitmap( bm.getBitmap(), i*(cellWidth-2), j*(cellHeight-2), cellWidth-2, cellHeight-2) );
                
                ImageButton btn = new ImageButton(this);
                
                btn.setImageDrawable(img); 
                btn.setOnClickListener(this);
                btn.setPadding(1, 1, 1, 1);
                
                btn.setTag((i*picturePuzzleHeight+j*picturePuzzleWidth));
                
                picture[i][j] = btn;
                
                AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(cellWidth, cellHeight, i*cellWidth, j*cellHeight);
        		
                mainViewGroup.addView(picture[i][j], params); 
        		
        		if((i+j)==(picturePuzzleHeight+picturePuzzleWidth-3))
        		{
        			picture[picturePuzzleHeight-1][picturePuzzleWidth-1]=null;
        			break;        			
        		}
        	}
        
        }
        
	}
	
	private boolean kiemTra(){
		
		for(int i=0;i<picturePuzzleHeight;i++){   
        	for(int j=0;j<picturePuzzleWidth;j++){
        		if(picture[i][j]!=null&&((Integer)picture[i][j].getTag())!=(i*picturePuzzleHeight+j*picturePuzzleWidth))
        			return false;
        	}
		}
		
		return true;
	}
	
	public void tronGame(View v){
	
		Random random = new Random();
        int num1;
        int num2;
        for(int i=0;i<picturePuzzleHeight;i++){
            
        	for(int j=0;j<picturePuzzleWidth;j++){
        		
        		num1 = random.nextInt();
        		num1 = num1<0?(-num1):num1;
        		num1 = num1%picturePuzzleHeight;
        		
        		num2 = random.nextInt();
        		num2 = num2<0?(-num2):num2;
        		num2 = num2%picturePuzzleWidth;
        		
        		ImageButton tmp = picture[i][j];
        		
        		picture[i][j] = picture[num1][num2];
        		picture[num1][num2] = tmp;
        		
        		if(picture[i][j]!=null)
        		{        		
        			AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(cellWidth, cellHeight, i*cellWidth, j*cellHeight);       		
            		picture[i][j].setLayoutParams(params);        		
        		}
        		
        		if(picture[num1][num2]!=null)
        		{  
        			AbsoluteLayout.LayoutParams	params = new AbsoluteLayout.LayoutParams(cellWidth, cellHeight, num1*cellWidth, num2*cellHeight);
            		picture[num1][num2].setLayoutParams(params);
        		}        			
        	
        	}
        }
	
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageButton btn = (ImageButton)v;
		
		AbsoluteLayout.LayoutParams	btnParams = (AbsoluteLayout.LayoutParams)v.getLayoutParams();
		
		int x1,y1,x2,y2;
		
		x1 = btnParams.x/cellWidth;
		y1 = btnParams.y/cellHeight;
		
		x2=y2=-1;
		
		
		if(x1<(picturePuzzleHeight-1)&&picture[x1+1][y1]==null){
			x2=x1+1;
			y2=y1;
		} else if(x1>0&&picture[x1-1][y1]==null){
			x2=x1-1;
			y2=y1;			
		} else if(y1<(picturePuzzleWidth-1)&&picture[x1][y1+1]==null){
			x2=x1;
			y2=y1+1;
		} else if(y1>0&&picture[x1][y1-1]==null){
			x2=x1;
			y2=y1-1;			
		}
		
		if(x2==-1||y2==-1)
			return;
		
		ImageButton tmp = picture[x1][y1];
		picture[x1][y1] = picture[x2][y2];
		picture[x2][y2] = tmp;
		
		if(picture[x1][y1]!=null)
		{        		
			AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(cellWidth, cellHeight, x1*cellWidth, y1*cellHeight);       		
    		picture[x1][y1].setLayoutParams(params);        		
		}
		
		if(picture[x2][y2]!=null)
		{  
			AbsoluteLayout.LayoutParams	params = new AbsoluteLayout.LayoutParams(cellWidth, cellHeight, x2*cellWidth, y2*cellHeight);
    		picture[x2][y2].setLayoutParams(params);
		}
		
		if(kiemTra())
		{
			Toast toast = Toast.makeText(this, "Chúc mừng bạn đã hoàn tất trò chơi :)",Toast.LENGTH_LONG);
			toast.show();
		}
		
				
	}
	
}