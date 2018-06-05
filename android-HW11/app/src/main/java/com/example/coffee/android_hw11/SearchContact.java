package com.example.coffee.android_hw11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchContact extends Fragment {

    private ListView mListData;
    private HighlightAdapter<String> contactListAdapter;
    private ArrayList<ContentValues> contactList;
    private int longClickedItemIndex;

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
        View view =  inflater.inflate(R.layout.fragment_search_contact, container, false);
        mListData = (ListView) view.findViewById(R.id.listData);
        registerForContextMenu(mListData);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        contactListAdapter = new HighlightAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);
        contactList = new ArrayList<>();

        mListData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListData.clearChoices();
        // !!! 設定 OnItemLongClickListener "可能"導致無法使用 Context Menu，解決方法在下 !!!
        mListData.setOnItemLongClickListener(listDataOnLongClickListener);
        mListData.setAdapter(contactListAdapter);

        Cursor cursor = MainActivity.mContentResolver.query(ContactContentProvider.CONTENT_URI, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name, phoneNumber, typeOfPhoneNumber;
                name = cursor.getString(1);
                phoneNumber = cursor.getString(2);
                typeOfPhoneNumber = cursor.getString(3);

                String data = "Name: " + name + "\n" +
                        "Phone Number: " + phoneNumber + "\n" +
                        "Type Of Phone Number: " + typeOfPhoneNumber;
                contactListAdapter.add(data);

                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("phoneNumber", phoneNumber);
                contentValues.put("typeOfPhoneNumber", typeOfPhoneNumber);
                contactList.add(contentValues);

                cursor.moveToNext();
            }
            cursor.close();
        }
        contactListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.content_menu, menu);
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public ContentValues getLongClickedItem() {
        return contactList.get(longClickedItemIndex);
    }

    public void deleteLongClickedItem() {
        ContentValues longClickedItem = contactList.get(longClickedItemIndex);
        contactListAdapter.remove("Name: " + longClickedItem.getAsString("name") + "\n" +
                "Phone Number: " + longClickedItem.getAsString("phoneNumber") + "\n" +
                "Type Of Phone Number: " + longClickedItem.getAsString("typeOfPhoneNumber"));
        contactList.remove(longClickedItemIndex);
        contactListAdapter.notifyDataSetChanged();
    }

    private ListView.OnItemLongClickListener listDataOnLongClickListener = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            longClickedItemIndex = position;
            return false; // !!! 必須回傳false 否則無法呼叫 context menu !!! // CANNOT consumed the long click
        }
    };

    public void addContact(ContentValues data) {
        contactList.add(data);
        contactListAdapter.add("Name: " + data.getAsString("name") + "\n" +
                        "Phone Number: " + data.getAsString("phoneNumber") + "\n" +
                        "Type Of Phone Number: " + data.getAsString("typeOfPhoneNumber"));
        contactListAdapter.notifyDataSetChanged();
        mListData.clearChoices();
        mListData.requestLayout();
    }

    public void setHighlighter(String highlighter) {
        contactListAdapter.setHighlighter(highlighter);
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