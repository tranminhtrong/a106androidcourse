package android.uiexample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class DateTimePickerExampleActivity extends Activity{
	
	int hour, minute;
    int Year=2010, month=1, day=1;
 
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
 
    @Override  
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.datetimepicker);
        showDialog(DATE_DIALOG_ID);
    }
 
    @Override    
    protected Dialog onCreateDialog(int id) 
    {
        switch (id) {
            case TIME_DIALOG_ID: 
                return new TimePickerDialog(
                    this, mTimeSetListener, hour, minute, false);
 
            case DATE_DIALOG_ID: 
                return new DatePickerDialog(
                    this, mDateSetListener, Year, month, day);
        }
        return null;    
    }
 
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() 
    {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			
		}   
        //...
    };
 
    private DatePickerDialog.OnDateSetListener mDateSetListener =
    new DatePickerDialog.OnDateSetListener() 
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) 
        {
            Year = year;
            month = monthOfYear;
            day = dayOfMonth;
            Toast.makeText(getBaseContext(), 
                    "You have selected : " + (month + 1) +
                    "/" + day + "/" + Year,
                    Toast.LENGTH_SHORT).show();
        }
    };

}
