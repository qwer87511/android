package com.example.coffee.android_hw11;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class AddNewContact extends Fragment {

    private Activity activity;
    private EditText mEdtName;
    private EditText mEdtPhoneNumber;
    private Spinner mSpnType;

    public AddNewContact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_contact, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        activity = getActivity();

        mEdtName = (EditText) view.findViewById(R.id.edtName);
        mEdtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        mSpnType = (Spinner) view.findViewById(R.id.spnPhoneNumberType);
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        // 命名需和 sql 宣告時一樣
        contentValues.put("name", mEdtName.getText().toString());
        contentValues.put("phoneNumber", mEdtPhoneNumber.getText().toString());
        contentValues.put("typeOfPhoneNumber", mSpnType.getSelectedItem().toString());
        return contentValues;
    }

    public void setContentValues(ContentValues contentValues) {
        mEdtName.setText(contentValues.getAsString("name"));
        mEdtPhoneNumber.setText(contentValues.getAsString("phoneNumber"));
        mSpnType.setSelection(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.phoneNumberType)))
                .indexOf(contentValues.getAsString("typeOfPhoneNumber")));
    }
}
