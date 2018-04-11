package edu.ntut.user.myhw3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadSex;
    private RadioButton mRadBtnMale;
    private RadioButton mRadBtnFemale;
    private RadioGroup mRadGrp;
    private RadioButton mRadBtnAgeRange1;
    private RadioButton mRadBtnAgeRange2;
    private RadioButton mRadBtnAgeRange3;
    private TextView mTxtNumFamily;
    private NumberPicker mNumPkrFamily;
    private Spinner mSpnAge;
    private CheckBox mChe1;
    private CheckBox mChe2;
    private CheckBox mChe3;
    private CheckBox mChe4;
    private CheckBox mChe5;
    private CheckBox mChe6;
    private CheckBox mChe7;
    private CheckBox mChe8;
    private CheckBox mChe9;
    private CheckBox mChe10;
    private Button mBtnOK;
    private TextView mTxtSug;
    private TextView mTxtHob;
    private String selectedSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mSpnSex = (Spinner) findViewById(R.id.spnSex);
        mRadSex = (RadioGroup) findViewById(R.id.radSex);
        mRadBtnMale = (RadioButton) findViewById(R.id.radBtnMale);
        mRadBtnFemale = (RadioButton) findViewById(R.id.radBtnFemale);
        mRadGrp = (RadioGroup) findViewById(R.id.radGrpAge);
        mRadBtnAgeRange1 = (RadioButton) findViewById(R.id.radBtnAgeRange1);
        mRadBtnAgeRange2 = (RadioButton) findViewById(R.id.radBtnAgeRange2);
        mRadBtnAgeRange3 = (RadioButton) findViewById(R.id.radBtnAgeRange3);
        mTxtNumFamily = (TextView) findViewById(R.id.txtNumFamily);
        mNumPkrFamily = (NumberPicker) findViewById(R.id.numPkrFamily);
        mNumPkrFamily.setMinValue(0);
        mNumPkrFamily.setMaxValue(20);
        mNumPkrFamily.setValue(3);
        mSpnAge = (Spinner)findViewById(R.id.spnAge);
        mChe1=(CheckBox)findViewById(R.id.che1);
        mChe2=(CheckBox)findViewById(R.id.che2);
        mChe3=(CheckBox)findViewById(R.id.che3);
        mChe4=(CheckBox)findViewById(R.id.che4);
        mChe5=(CheckBox)findViewById(R.id.che5);
        mChe6=(CheckBox)findViewById(R.id.che6);
        mChe7=(CheckBox)findViewById(R.id.che7);
        mChe8=(CheckBox)findViewById(R.id.che8);
        mChe9=(CheckBox)findViewById(R.id.che9);
        mChe10=(CheckBox)findViewById(R.id.che10);
        mBtnOK = (Button) findViewById(R.id.btnOK);
        mTxtSug = (TextView) findViewById(R.id.txtSug);
        mTxtHob=(TextView)findViewById(R.id.txtHob);
        mBtnOK.setOnClickListener(btnOKOnClick);
    }

    private AdapterView.OnItemSelectedListener spnOnItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedSex = parent.getSelectedItem().toString();

            switch (selectedSex) {
                case "male": mRadBtnAgeRange1.setText(getString(R.string.maleAgeRange1));
                    mRadBtnAgeRange2.setText(getString(R.string.maleAgeRange2));
                    mRadBtnAgeRange3.setText(getString(R.string.maleAgeRange3));
                    break;
                case "female":
                    mRadBtnAgeRange1.setText(getString(R.string.femaleAgeRange1));
                    mRadBtnAgeRange2.setText(getString(R.string.femaleAgeRange2));
                    mRadBtnAgeRange3.setText(getString(R.string.femaleAgeRange3));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnClickListener btnOKOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MarriageSuggestion ms=new MarriageSuggestion();
            String ageRange=mSpnAge.getSelectedItem().toString();
            String hobby="你的興趣:";
            int sexType=0;
            String strSex = "";
            switch (mRadSex.getCheckedRadioButtonId()) {
                case R.id.radBtnMale:
                    strSex = "Male";
                    break;
                case R.id.radBtnFemale:
                    strSex = "Female";
                    break;
            }

            int iAgeRange = 0;
            switch ((int)mSpnAge.getSelectedItemId())
            {
                case R.id.radBtnAgeRange1:
                    iAgeRange = 1;
                    break;
                case R.id.radBtnAgeRange2:
                    iAgeRange = 2;
                    break;
                case R.id.radBtnAgeRange3:
                    iAgeRange = 3;
                    break;
            }

            if(mChe1.isChecked())
                hobby+= mChe1.getText().toString()+" ";
            if(mChe2.isChecked())
                hobby+= mChe2.getText().toString()+" ";
            if(mChe3.isChecked())
                hobby+= mChe3.getText().toString()+" ";
            if(mChe4.isChecked())
                hobby+= mChe4.getText().toString()+" ";
            if(mChe5.isChecked())
                hobby+= mChe5.getText().toString()+" ";
            if(mChe6.isChecked())
                hobby+= mChe6.getText().toString()+" ";
            if(mChe7.isChecked())
                hobby+= mChe7.getText().toString()+" ";
            if(mChe8.isChecked())
                hobby+= mChe8.getText().toString()+" ";
            if(mChe9.isChecked())
                hobby+= mChe9.getText().toString()+" ";
            if(mChe10.isChecked())
                hobby+= mChe10.getText().toString()+" ";
            mTxtSug.setText(ms.getSuggestion(strSex, iAgeRange, mNumPkrFamily.getValue()));
            mTxtHob.setText(hobby);
        }
    };
}
