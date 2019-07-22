package com.karimsabitov.kanclerproducts.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.karimsabitov.kanclerproducts.db.DBSchema.ProductsTable;
import com.karimsabitov.kanclerproducts.model.Product;

public class PurchasesCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public PurchasesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        String title = getString(getColumnIndex(ProductsTable.Cols.TITLE));
        int count = getInt(getColumnIndex(ProductsTable.Cols.COUNT));
        boolean in_bucket = getInt(getColumnIndex(ProductsTable.Cols.IN_BUCKET)) != 0;
        int amount = getInt(getColumnIndex(ProductsTable.Cols.AMOUNT));
        return new Product(title, count, amount, in_bucket);
    }
}
