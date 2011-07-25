package niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class main extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        CustomView graphicsView;
		//graphicsView = new GraphicView(this);
        //setContentView(graphicsView);
		setContentView(R.layout.main);
		graphicsView = (CustomView)findViewById(R.id.graphicView);
        graphicsView.batDauDiChuyenBanh();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}