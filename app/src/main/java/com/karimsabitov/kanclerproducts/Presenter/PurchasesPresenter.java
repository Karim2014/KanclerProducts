package com.karimsabitov.kanclerproducts.Presenter;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.karimsabitov.kanclerproducts.MainActivity;
import com.karimsabitov.kanclerproducts.Utils.SORTING_TYPE;
import com.karimsabitov.kanclerproducts.model.Product;
import com.karimsabitov.kanclerproducts.model.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchasesPresenter implements PurchasesContract.Presenter {

    private PurchasesContract.View mView;
    private PurchasesContract.Repository mRepository;

    public PurchasesPresenter(PurchasesContract.View view) {
        mView = view;
        mRepository = new Repository(((MainActivity) mView));
    }

    @Override
    public void setupAdapter() {
        setupAdapter(SORTING_TYPE.ASC);
    }

    @Override
    public void importFile(final String file) {
        new ImportTask(file).execute();
    }

    @Override
    public void updateProduct(Product product) {
        mRepository.updateProduct(product);
    }

    @Override
    public void setupAdapter(SORTING_TYPE sorting_type) {
        new FetchProducts(sorting_type).execute();
    }

    private class ImportTask extends AsyncTask<Void, Void, List<Product>> {

        String fileName;

        ProgressDialog progressDialog;

        ImportTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        protected void onPreExecute() {
             progressDialog = ProgressDialog.show(((MainActivity) mView), "Импорт", "Выполняется загрузка", true, false);
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            List<Product> products = new ArrayList<>();
            try {
                products = mRepository.importFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mRepository.addProducts(products);
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            mView.setupAdapter(products);
            progressDialog.dismiss();
        }
    }

    private class FetchProducts extends AsyncTask<Void, Void, List<Product>> {

        SORTING_TYPE mSORTING_type;

        public FetchProducts(SORTING_TYPE sorting_type) {
            mSORTING_type = sorting_type;
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            return mRepository.getProducts(mSORTING_type);
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            mView.setupAdapter(products);
        }
    }
}
