package a.b;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class main extends Activity {
	
	TextView textView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.editText);	
    }
    
    public void nhapSo(View v){
    	String str = ((Button)v).getText().toString();
    	textView.append(str);
    }
    
    public void tinhToan(View v){
    	String str = textView.getText().toString();
    	String tt = null;
    	if(str.contains("+"))
    		tt = "+";
    	else if(str.contains("-"))
    		tt = "-";
    	else if(str.contains("*"))
    		tt = "*";
    	else if(str.contains("/"))
    		tt = "/";
    	
    	if(tt==null)
    		return;
    	
    	String[] th = str.split("["+tt+"]");
    	
    	if(tt.equals("+")){
    		int tong = Integer.valueOf(th[0]) + Integer.valueOf(th[1]);
    		textView.setText(String.valueOf(tong));
    	} else if(tt.equals("-")){
    		int tong = Integer.valueOf(th[0]) - Integer.valueOf(th[1]);
    		textView.setText(String.valueOf(tong));
    	} else if(tt.equals("*")){
    		int tong = Integer.valueOf(th[0]) * Integer.valueOf(th[1]);
    		textView.setText(String.valueOf(tong));
    	} else if(tt.equals("/")){
    		int tong = Integer.valueOf(th[0]) / Integer.valueOf(th[1]);
    		textView.setText(String.valueOf(tong));
    	}
    	
    	
    }
    
    public void xoaManHinh(View v){
    	textView.setText("");
    }
    
}