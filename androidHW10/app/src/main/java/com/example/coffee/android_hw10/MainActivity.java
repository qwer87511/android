package com.example.coffee.android_hw10;

import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    enum FRAGMENT_ID { ADD_NEW_CONTENT, SEARCH_CONTACT };
    FRAGMENT_ID fragment_id;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AddNewContact addNewContact;
    private SearchContact searchContact;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_id = FRAGMENT_ID.ADD_NEW_CONTENT;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        addNewContact = new AddNewContact();
        searchContact = new SearchContact();

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //mTabLayout.addOnTabSelectedListener(tabSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case Menu.FIRST:
                ContentValues data = addNewContact.getContentValues();
                searchContact.addContact(
                                    "Name: " + data.getAsString("Name") +
                                            ", PhoneNumber: " + data.getAsString("Phone number") +
                                            ", PhoneType: " + data.getAsString("Type of phone number"));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            //inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

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
