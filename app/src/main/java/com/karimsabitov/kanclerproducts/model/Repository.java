package com.karimsabitov.kanclerproducts.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.karimsabitov.kanclerproducts.Presenter.PurchasesContract;
import com.karimsabitov.kanclerproducts.Utils.ImportManager;
import com.karimsabitov.kanclerproducts.Utils.SORTING_TYPE;
import com.karimsabitov.kanclerproducts.db.DBSchema.ProductsTable;
import com.karimsabitov.kanclerproducts.db.PurchaseDBHelper;
import com.karimsabitov.kanclerproducts.db.PurchasesCursorWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository implements PurchasesContract.Repository {

    private static final String KEY_ITEM = "ITEM";

    private Context mContext;
    private SQLiteDatabase mDB;

    public Repository(Context context) {
        mContext = context.getApplicationContext();
        mDB = new PurchaseDBHelper(mContext).getWritableDatabase();
    }


    @Override
    public List<Product> importFile(String fileName) throws IOException {
        return new ImportManager().fetchProducts(fileName);
    }

    @Override
    public List<Product> getProducts(SORTING_TYPE SORTING_type) {

        ArrayList<Product> arrayList = new ArrayList<>();

        String sorting_type_str = "ASC";
        String selection_clause = null;

        switch (SORTING_type) {
            case ASC:
                sorting_type_str = "ASC";
                break;
            case DESC:
                sorting_type_str = "DESC";
                break;
            case CHECKED:
                selection_clause = ProductsTable.Cols.IN_BUCKET + " = 1";
                break;
            case UNCHECKED:
                selection_clause = ProductsTable.Cols.IN_BUCKET + " = 0";
                break;
            default:
                sorting_type_str = "ASC";
        }

        PurchasesCursorWrapper cursor = new PurchasesCursorWrapper(mDB.query(
                ProductsTable.NAME,
                null,
                selection_clause, null, null, null, ProductsTable.Cols.TITLE + " " + sorting_type_str));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrayList.add(cursor.getProduct());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return arrayList;
    }

    @Override
    public void addProducts(List<Product> product)  {
        mDB.delete(ProductsTable.NAME, null, null);
        for (Product product1 : product) {
            mDB.insert(ProductsTable.NAME, null, getContentValues(product1));
        }
    }

    @Override
    public void updateProduct(Product product) {
        mDB.update(
                ProductsTable.NAME,
                getContentValues(product),
                ProductsTable.Cols.TITLE + " LIKE ?",
                new String[]{product.getTitle()}
                );
    }

    private ContentValues getContentValues(Product product) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProductsTable.Cols.TITLE, product.getTitle());
        contentValues.put(ProductsTable.Cols.COUNT, product.getCount());
        contentValues.put(ProductsTable.Cols.IN_BUCKET, product.isInBuscket());
        contentValues.put(ProductsTable.Cols.AMOUNT, product.getAmount());

        return contentValues;
    }
}
