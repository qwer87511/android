package com.example.coffee.android_hw10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String DB_FILE = "contact.db", DB_TABLE = "contact";
    public static SQLiteDatabase mFriendDB;
    private TabLayout mTabLayout;
    private AddNewContact addNewContact;
    private SearchContact searchContact;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(tabSelectedListener);

        addNewContact = new AddNewContact();
        searchContact = new SearchContact();

        FriendDbOpenHelper friendDbOpenHelper = new FriendDbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        mFriendDB = friendDbOpenHelper.getWritableDatabase();
        Cursor cursor = mFriendDB.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);

        if(cursor != null) {
            if (cursor.getCount() == 0) {
                // TEXT 命名須和 ContentValue 一樣
                mFriendDB.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "phoneNumber TEXT," +
                        "typeOfPhoneNumber TEXT);");
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menuItemSearch).getActionView();
        searchView.setOnQueryTextListener(searchViewOnQueryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuItemAdd:
                ContentValues data = addNewContact.getContentValues();
                mFriendDB.insert(DB_TABLE, null, data);
                searchContact.addContact( "Name: " + data.getAsString("name") + "\n" +
                        "Phone Number: " + data.getAsString("phoneNumber") + "\n" +
                        "Type Of Phone Number: " + data.getAsString("typeOfPhoneNumber"));
                Toast.makeText(MainActivity.this, "新增聯絡人成功", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFriendDB.close();
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if(tab.getPosition() == 1) {
                // Close keyboard when user click TabLayout
                InputMethodManager inputMethodManager = ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE));
                if(inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(MainActivity.this.getCurrentFocus()).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private SearchView.OnQueryTextListener searchViewOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if(query.equals(""))
                return true;

            Cursor cursor = mFriendDB.query(true, DB_TABLE, new String[]{"name", "phoneNumber", "typeOfPhoneNumber"},
                    "name=\"" + query + "\"", null,null,null,null,null,null);

            if(cursor == null)
                return true;

            if(cursor.getCount() == 0) { // 沒有找到
                Toast.makeText(MainActivity.this, "the contact is not found", Toast.LENGTH_LONG).show();
            }
            else {  // 找到了
                cursor.moveToFirst();
                String data = "Name: " + cursor.getString(0) + "\n" +
                        "Phone Number: " + cursor.getString(1) + "\n" +
                        "Type Of Phone Number: " + cursor.getString(2);
                searchContact.setHighlighter(data);
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
            }
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    };

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return addNewContact;
                case 1:
                    return searchContact;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.addingANewContact);
                case 1:
                    return getString(R.string.searchContact);
                default:
                    return null;
            }
        }
    }
}
