package com.example.coffee.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtSex, mEdtAge;
    private Button mBtn;
    private TextView mTxtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtSex = (EditText) findViewById(R.id.edtSex);
        mEdtAge = (EditText) findViewById(R.id.edtAge);
        mBtn = (Button) findViewById(R.id.btn);
        mTxtResult = (TextView) findViewById(R.id.txtResult);

        mBtn.setOnClickListener(btnOnClick);
    }

    private View.OnClickListener btnOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String strSex = mEdtSex.getText().toString();
            int iAge = Integer.parseInt(mEdtAge.getText().toString());

            String strSug = getString(R.string.suggestion);

            if(strSex.equals(getString(R.string.sex_male))) {
                if(iAge<28)
                    strSug += getString(R.string.sug_not_hurry);
                else if(iAge > 33)
                    strSug += getString(R.string.sug_get_married);
                else
                    strSug += getString(R.string.sug_find_couple);
            }
            else {
                if(iAge<25)
                    strSug += getString(R.string.sug_not_hurry);
                else if(iAge > 30)
                    strSug += getString(R.string.sug_get_married);
                else
                    strSug += getString(R.string.sug_find_couple);
            }

            mTxtResult.setText(strSug);
        }
    };
}
