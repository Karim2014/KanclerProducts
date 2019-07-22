package com.karimsabitov.kanclerproducts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.karimsabitov.kanclerproducts.db.DBSchema.ProductsTable;

public class PurchaseDBHelper extends SQLiteOpenHelper {

    private static final String NAME = "Purchases.db";
    private static final int DB_VERSION = 1;


    public PurchaseDBHelper(@Nullable Context context) {
        super(context, NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ProductsTable.NAME + "(" +
                ProductsTable.Cols.TITLE + " varchar(50)," +
                ProductsTable.Cols.COUNT + " integer DEFAULT 0, " +
                ProductsTable.Cols.IN_BUCKET + " BOOL(1) DEFAULT 0, " +
                ProductsTable.Cols.AMOUNT + " integer DEFAULT 0" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
