package com.example.coffee.android_hw10;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewContact extends Fragment {

    private View view;
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

        view = getView();
        activity = getActivity();

        mEdtName = (EditText) view.findViewById(R.id.edtName);
        mEdtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        mSpnType = (Spinner) view.findViewById(R.id.spnPhoneNumberType);
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity.getString(R.string.name), mEdtName.getText().toString());
        contentValues.put(activity.getString(R.string.phoneNumber), mEdtName.getText().toString());
        contentValues.put(activity.getString(R.string.typeOfPhoneNumber), mSpnType.getSelectedItem().toString());
        return contentValues;
    }
}
