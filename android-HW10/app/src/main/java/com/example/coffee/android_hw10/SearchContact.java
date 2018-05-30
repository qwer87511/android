package com.example.coffee.android_hw10;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchContact extends Fragment {

    private ListView mListData;
    private HighlightAdapter<String> contactData;

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
        View view = getView();
        contactData = new HighlightAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);
        mListData = (ListView) view.findViewById(R.id.listData);
        mListData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListData.clearChoices();
        mListData.setAdapter(contactData);
        Cursor cursor = MainActivity.mFriendDB.rawQuery("select * from " + MainActivity.DB_TABLE,null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String data = "Name: " + cursor.getString(1) + "\n" +
                        "Phone Number: " + cursor.getString(2) + "\n" +
                        "Type Of Phone Number: " + cursor.getString(3);
                contactData.add(data);
                cursor.moveToNext();
            }
            cursor.close();
        }
        contactData.notifyDataSetChanged();
    }

    public void addContact(String data) {
        contactData.add(data);
        contactData.notifyDataSetChanged();
        mListData.clearChoices();
        mListData.requestLayout();
    }

    public void setHighlighter(String highlighter) {
        contactData.setHighlighter(highlighter);
    }

    private class HighlightAdapter<T> extends ArrayAdapter<T> {

        String highlighter;

        public HighlightAdapter(Context context, int resource) {
            super(context, resource);
        }

        public void setHighlighter(String highlighter) {
            this.highlighter = highlighter;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            String item = getItem(position).toString();

            if(item.equals(highlighter)) {
                view.setBackgroundResource(R.color.colorAccent);
            }
            else {
                view.setBackgroundResource(R.color.colorWhite);
            }

            return view;
        }
    }
}