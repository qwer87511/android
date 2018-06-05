package com.example.coffee.android_hw11;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static ContentResolver mContentResolver;

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

        mContentResolver = getContentResolver();
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
                if (data.getAsString("name").equals(""))
                    return true;
                mContentResolver.insert(ContactContentProvider.CONTENT_URI, data);
                searchContact.addContact(data);
                Toast.makeText(MainActivity.this, "新增聯絡人成功", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemModify:
                // 將資料傳進 AddNewContact 中 並切換頁面
                addNewContact.setContentValues(searchContact.getLongClickedItem());
                mViewPager.setCurrentItem(0);
                //searchContact.deleteLongClickedItem();
                //mContentResolver.delete(ContactContentProvider.CONTENT_URI, "name = " + searchContact.getLongClickedItem().getAsString("name"), null);
                Toast.makeText(MainActivity.this, "請重新提交此聯絡人資料", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuItemDelete:
                mContentResolver.delete(ContactContentProvider.CONTENT_URI, "name = " + searchContact.getLongClickedItem().getAsString("name"), null);
                searchContact.deleteLongClickedItem();
                Toast.makeText(MainActivity.this, "刪除聯絡人成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
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

            // 沒有輸入資料
            if(query.equals("")) {
                Toast.makeText(MainActivity.this, "Please input name", Toast.LENGTH_LONG).show();
                return true;
            }

            // 切換到Search頁面
            if(mViewPager.getCurrentItem() != 1){
                mViewPager.setCurrentItem(1);
            }

            // 關閉鍵盤
            InputMethodManager inputMethodManager = ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE));
            if(inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(MainActivity.this.getCurrentFocus()).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

            // 從 SQL 查詢
            Cursor cursor = mContentResolver.query(ContactContentProvider.CONTENT_URI, new String[]{"name", "phoneNumber", "typeOfPhoneNumber"},
                    "name=\"" + query + "\"", null, null);

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
            }
            cursor.close();
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
