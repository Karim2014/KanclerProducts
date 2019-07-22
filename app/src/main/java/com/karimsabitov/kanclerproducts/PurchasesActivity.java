package com.karimsabitov.kanclerproducts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.karimsabitov.kanclerproducts.Presenter.PurchasesContract;
import com.karimsabitov.kanclerproducts.Presenter.PurchasesPresenter;
import com.karimsabitov.kanclerproducts.UI.PurchaseAdapter;
import com.karimsabitov.kanclerproducts.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchasesActivity extends AppCompatActivity implements PurchasesContract.View {



    private static final String EXTRA_DATE = "com.karimsabitov.purchasesactivity.extra_date";
    private static final String TAG = "Test";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    PurchaseAdapter mPurchaseAdapter;
    PurchasesContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String date = (String) getIntent().getSerializableExtra(EXTRA_DATE);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = new PurchasesPresenter(this);
        //mPresenter.setupAdapter(date);

    }


    public static Intent newIntent(Context context, String date) {
        Intent intent = new Intent(context, PurchasesActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        return intent;
    }

    @Override
    public void setupAdapter(List<Product> products) {
        if (mPurchaseAdapter != null) {
            mPurchaseAdapter.setItems(products);
            mPurchaseAdapter.notifyDataSetChanged();
            Log.d(TAG, "setupAdapter: notify");
        }else {
            mPurchaseAdapter = new PurchaseAdapter(this, mPresenter, products);
            mRecyclerView.setAdapter(mPurchaseAdapter);
            Log.d(TAG, "setupAdapter: new");
        }
    }
}
