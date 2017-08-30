package com.smrecki.payconiqtest.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by tomislav on 30/08/2017.
 */
@Database(version = AppDatabase.VERSION, name = AppDatabase.NAME)
public class AppDatabase {
    public static final String NAME = "PayconiqDB";
    public static final int VERSION = 1;
}
