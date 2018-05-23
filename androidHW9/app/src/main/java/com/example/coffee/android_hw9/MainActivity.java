package com.example.coffee.android_hw9;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
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

    private static final int MENU_MUSIC = Menu.FIRST,
            MENU_PLAY_MUSIC = Menu.FIRST + 1,
            MENU_STOP_MUSIC = Menu.FIRST + 2,
            MENU_ABOUT = Menu.FIRST + 3,
            MENU_EXIT = Menu.FIRST + 4;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, R.string.backgroundMusic)
                .setIcon(android.R.drawable.ic_media_ff);
        subMenu.add(0, MENU_PLAY_MUSIC, 0, R.string.playBackgroundMusic);
        subMenu.add(0, MENU_STOP_MUSIC, 1, R.string.stopBackgroundMusic);
        menu.add(0, MENU_ABOUT, 1, R.string.about);
        menu.add(0, MENU_EXIT, 2, R.string.exit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_PLAY_MUSIC:
                Intent intent = new Intent(MainActivity.this, MediaPlayService.class);
                startService(intent);
                return true;
            case MENU_STOP_MUSIC:
                intent = new Intent(MainActivity.this, MediaPlayService.class);
                stopService(intent);
                return true;
            case MENU_ABOUT:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("關於這個程式")
                        .setMessage("選單範例程式")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                        .show();
                return true;
            case MENU_EXIT:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
