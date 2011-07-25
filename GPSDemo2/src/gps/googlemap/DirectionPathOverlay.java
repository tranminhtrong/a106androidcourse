package gps.googlemap;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;


public class DirectionPathOverlay extends Overlay{
	private GeoPoint geoPointFrom, geoPointTo;
	private int mRadius = 6;
	private int mode = 0;
	private int defaultColor;
	private String text = "";
	private Bitmap bitmap = null;
//	private Context context;
	
	GPSMain itsMapActivity = null;
	Paint pathPaint = null;
//	private Bitmap PUSHPIN_SRC = null;
//	private Bitmap PUSHPIN_DEST = null;
	
	public DirectionPathOverlay(GeoPoint geoPointFrom, GeoPoint geoPointTo, int mode){
		this.geoPointFrom = geoPointFrom;
		this.geoPointTo = geoPointTo;
		this.mode = mode;
		defaultColor = 999;		// no default color
	}
	
	public DirectionPathOverlay(GeoPoint geoPointFrom, GeoPoint geoPointTo, int mode, int defaultColor){
		this.geoPointFrom = geoPointFrom;
		this.geoPointTo = geoPointTo;
		this.mode = mode;
		this.defaultColor = defaultColor;		
	}
	
	/*public DirectionPathOverlay(GPSMain map){
		itsMapActivity = map;
		this.pathPaint = new Paint();
		this.pathPaint.setAntiAlias(true);
		
		PUSHPIN_SRC = BitmapFactory.decodeResource(this.itsMapActivity.getResources(), R.drawable.greena);
		PUSHPIN_DEST = BitmapFactory.decodeResource(this.itsMapActivity.getResources(), R.drawable.greenb);
	}*/
	
	public void SetText(String text){
		this.text = text;
	}
	
	public void SetBitMap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public int GetMode(){
		return mode;
	}
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		super.draw(canvas, mapView, shadow, when);
		
		Projection projection = mapView.getProjection();
		if(shadow == false){
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			
			// Convert geo coordinates to screen pixels
			Point pointSrc = new Point();
			projection.toPixels(geoPointFrom, pointSrc);
			
			if (mode == 1) {
				if (defaultColor == 999)
					paint.setColor(Color.BLUE);
				else
					paint.setColor(defaultColor);
				RectF oval = new RectF(pointSrc.x - mRadius, pointSrc.y - mRadius, pointSrc.x + mRadius, pointSrc.y + mRadius);
				
				// start point
				canvas.drawOval(oval, paint);	
				
				/*// read the image
				Bitmap markerImageSrc = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.greena);
				
				// draw it
				canvas.drawBitmap(PUSHPIN_SRC, 
						pointSrc.x - PUSHPIN_SRC.getWidth() / 2, 
						pointSrc.y - PUSHPIN_SRC.getHeight() / 2 , null);
				//return true;
*/				
			} else if (mode == 2){
				if (defaultColor == 999)
					paint.setColor(Color.RED);
				else
					paint.setColor(defaultColor);
				
				Point pointDest = new Point();
				projection.toPixels(geoPointTo, pointDest);
				paint.setStrokeWidth(5);
				paint.setAlpha(120);
				canvas.drawLine(pointSrc.x, pointSrc.y, pointDest.x, pointDest.y, paint);
			}
			else if (mode == 3){
				// the last path
				if (defaultColor == 999)
					paint.setColor(Color.RED);
				else
					paint.setColor(defaultColor);
				Point pointDest = new Point();
				projection.toPixels(geoPointTo, pointDest);
				paint.setStrokeWidth(5);
				paint.setAlpha(120);
				canvas.drawLine(pointSrc.x, pointSrc.y, pointDest.x, pointDest.y, paint);
				
				RectF oval=new RectF(pointDest.x - mRadius, pointDest.y - mRadius, pointDest.x + mRadius, pointDest.y + mRadius);
				
				// end point
				paint.setAlpha(225);
				canvas.drawOval(oval, paint);
				
				//Bitmap markerImageDest = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenb);
				//canvas.drawBitmap(PUSHPIN_DEST, pointDest.x - PUSHPIN_DEST.getWidth() / 2, pointDest.y - PUSHPIN_DEST.getHeight() / 2 , null);
				
			}			
		}		
		return true;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
	}
	
	
}
