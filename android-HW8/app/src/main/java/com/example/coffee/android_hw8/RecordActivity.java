package com.example.coffee.android_hw8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecordActivity extends AppCompatActivity {

    private Intent intent;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        listView = (ListView) findViewById(R.id.lstRecord);
        intent = getIntent();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(intent.getStringArrayListExtra("record"));
        listView.setAdapter(arrayAdapter);
    }
}
