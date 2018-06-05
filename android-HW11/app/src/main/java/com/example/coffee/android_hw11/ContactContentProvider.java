package com.example.coffee.android_hw11;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContactContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.ContactContentProvider";
    private static final String DB_FILE = "contact2.db", DB_TABLE = "contact2";
    private static final int URI_ROOT = 0, DB_TABLE_CONTACT = 1;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DB_TABLE);
    private static final UriMatcher sUriMatcher = new UriMatcher(URI_ROOT);
    static {
        sUriMatcher.addURI(AUTHORITY, DB_TABLE, DB_TABLE_CONTACT);
    }
    private SQLiteDatabase mContactDb;

    public ContactContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mContactDb.delete(DB_TABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if(sUriMatcher.match(uri) != DB_TABLE_CONTACT) {
            throw new IllegalArgumentException("Unknown URI" + uri);
        }

        long rowId = mContactDb.insert(DB_TABLE, null, values);
        Uri insertedRowUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(insertedRowUri, null);
        return insertedRowUri;
    }

    @Override
    public boolean onCreate() {
        FriendDbOpenHelper friendDbOpenHelper = new FriendDbOpenHelper(getContext(), DB_FILE, null, 1);
        mContactDb = friendDbOpenHelper.getWritableDatabase();

        // 檢查資料表是否存在，若不存在就建立一個
        Cursor cursor = mContactDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);

        if(cursor != null) {
            if (cursor.getCount() == 0) {
                // 建立資料表
                // TEXT 命名須和 ContentValue 一樣
                mContactDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "phoneNumber TEXT," +
                        "typeOfPhoneNumber TEXT);");
            }
            cursor.close();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(sUriMatcher.match(uri) != DB_TABLE_CONTACT) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor = mContactDb.query(true, DB_TABLE, projection, selection, null, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mContactDb.update(DB_TABLE, values, selection, selectionArgs);
    }
}
