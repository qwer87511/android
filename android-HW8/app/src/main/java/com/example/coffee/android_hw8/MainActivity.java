package com.example.coffee.android_hw8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity {

    private Spinner mSpnItem;
    private EditText mEdtDate;
    private EditText mEdtPrice;
    private Button mBtnEnter;
    private Button mBtnRecord;
    private ArrayList<String> record;
    private Formatter formatter;
    private StringBuilder stringBuilder;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpnItem = (Spinner) findViewById(R.id.spnItem);
        mEdtDate = (EditText) findViewById(R.id.edtDate);
        mEdtPrice = (EditText) findViewById(R.id.edtPrice);
        mBtnEnter = (Button) findViewById(R.id.btnEnter);
        mBtnRecord = (Button) findViewById(R.id.btnRecord);

        ((DatePicker) findViewById(R.id.datePicker)).setOnDateChangedListener(dpkOnDateChanged);
        ((CalendarView) findViewById(R.id.calendarView)).setOnDateChangeListener(cldOnDateChanged);

        mBtnEnter.setOnClickListener(btnEnterOnClick);
        mBtnRecord.setOnClickListener(btnRecordOnClick);

        record = new ArrayList<>();
        stringBuilder = new StringBuilder();
        formatter = new Formatter();
        num = 0;
    }

    private DatePicker.OnDateChangedListener dpkOnDateChanged = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String strDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            mEdtDate.setText(strDate);
        }
    };

    private CalendarView.OnDateChangeListener cldOnDateChanged = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String strDate = year + "/" + (month + 1) + "/" + dayOfMonth;
            mEdtDate.setText(strDate);
        }
    };

    private View.OnClickListener btnEnterOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strItem = mSpnItem.getSelectedItem().toString();
            String strDate = mEdtDate.getText().toString();
            String strPrice = mEdtPrice.getText().toString();

            String recordItem = "項目" + String.valueOf(num++) + "    " + strDate + "    " + strItem + "    " + strPrice;
            record.add(recordItem);
            Toast.makeText(MainActivity.this, strPrice, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnRecordOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            intent.putStringArrayListExtra("record", record);
            startActivity(intent);
        }
    };
}
