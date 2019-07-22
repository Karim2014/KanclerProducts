package com.karimsabitov.kanclerproducts.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.karimsabitov.kanclerproducts.Presenter.PurchasesContract;
import com.karimsabitov.kanclerproducts.R;
import com.karimsabitov.kanclerproducts.model.Product;

import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private PurchasesContract.Presenter mPresenter;

    public PurchaseAdapter(Context context, PurchasesContract.Presenter presenter, List<Product> products) {
        mContext = context;
        mPresenter = presenter;
        mProducts = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product product = mProducts.get(i);
        viewHolder.bindHolder(product);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void setItems(List<Product> items) {
        mProducts = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        Product mProduct;

        TextView mTitle;
        TextView mAmount;
        TextView mCount;
        CheckBox mCheckBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mAmount = itemView.findViewById(R.id.amount);
            mCount = itemView.findViewById(R.id.count);
            mCheckBox = itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void bindHolder(final Product product) {
            mProduct = product;

            mTitle.setText(mProduct.getTitle());
            mAmount.setText(String.valueOf(mProduct.getCount()));
            mCheckBox.setChecked(mProduct.isInBuscket());
            mCount.setText(String.valueOf(product.getAmount()));

            updateView(product);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mProduct.setInBuscket(isChecked);
                    mPresenter.updateProduct(product);
                }
            });

        }

        private void updateView(Product product) {
            if (product.getAmount() > 0 || product.getAmount() == product.getCount()) {
                mCount.setVisibility(View.VISIBLE);
            }else {
                mCount.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            mCheckBox.toggle();
        }

        @Override
        public boolean onLongClick(View v) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_num_picker, null);

            final MaterialNumberPicker numberPicker = view.findViewById(R.id.dialog_num_picker_picker);

            numberPicker.setMaxValue(mProduct.getCount());

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder
                    .setTitle("Введите количество")
                    .setView(view)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int N = numberPicker.getValue();
                            mProduct.setAmount(N);
                            mCount.setText(String.valueOf(N));
                            mPresenter.updateProduct(mProduct);
                            updateView(mProduct);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create()
                    .show();
            return false;
        }
    }
}
