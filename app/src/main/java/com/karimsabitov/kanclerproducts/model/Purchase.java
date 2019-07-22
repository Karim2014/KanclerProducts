package com.karimsabitov.kanclerproducts.model;

import java.util.Date;
import java.util.List;

public class Purchase {

    private Date mDate;
    private List<Product> mProducts;
    private double mPrice;

    public Purchase(Date date, List<Product> products, double price) {
        mDate = date;
        mProducts = products;
        mPrice = price;
    }
}
