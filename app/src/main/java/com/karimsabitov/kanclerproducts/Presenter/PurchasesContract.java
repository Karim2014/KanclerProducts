package com.karimsabitov.kanclerproducts.Presenter;

import com.karimsabitov.kanclerproducts.Utils.SORTING_TYPE;
import com.karimsabitov.kanclerproducts.model.Product;

import java.io.IOException;
import java.util.List;

public interface PurchasesContract {

    interface View {

        void setupAdapter(List<Product> products);
    }

    interface Presenter {

        void setupAdapter();

        void importFile(String file);

        void updateProduct(Product product);

        void setupAdapter(SORTING_TYPE sorting_type);
    }

    interface Repository {

        List<Product> importFile(String fileName) throws IOException;

        List<Product> getProducts(SORTING_TYPE SORTING_type);

        void addProducts(List<Product> product);

        void updateProduct(Product product);
    }

}
