package com.example.coffee.android_hw10;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchContact extends Fragment {

    private ListView mListData;
    private ArrayAdapter<String> contactData;


    public SearchContact() {
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
        return inflater.inflate(R.layout.fragment_search_contact, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        contactData = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);

        View view = getView();

        mListData = view.findViewById(R.id.listData);
        mListData.setAdapter(contactData);
    }

    public void addContact(String data) {
        contactData.add(data);
        contactData.notifyDataSetChanged();
        mListData.clearChoices();
        mListData.requestLayout();
    }
}