package com.karimsabitov.kanclerproducts.model;

public class Product {

    private String mTitle;
    private int mCount;
    private int mAmount;
    private boolean mIsInBuscket;

    public Product(String title, int count, int amount, boolean isInBuscket) {
        mTitle = title;
        mCount = count;
        mAmount = amount;
        mIsInBuscket = isInBuscket;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getCount() {
        return mCount;
    }

    public boolean isInBuscket() {
        return mIsInBuscket;
    }

    public void setInBuscket(boolean inBuscket) {
        mIsInBuscket = inBuscket;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }
}
