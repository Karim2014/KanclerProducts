package com.karimsabitov.kanclerproducts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.karimsabitov.kanclerproducts.Presenter.PurchasesContract;
import com.karimsabitov.kanclerproducts.Presenter.PurchasesPresenter;
import com.karimsabitov.kanclerproducts.UI.PurchaseAdapter;
import com.karimsabitov.kanclerproducts.Utils.SORTING_TYPE;
import com.karimsabitov.kanclerproducts.model.Product;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PurchasesContract.View, DialogSelectionListener {

    private static final String TAG = "Test";
    private static final int REQUEST_CODE_FILE_PICKER = 0;
    private PurchasesContract.Presenter mPresenter;

    private PurchaseAdapter mAdapter;

    @BindView(R.id.recycler_view_1)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new PurchasesPresenter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter.setupAdapter();
    }

    private void newPurchase() {

        DialogProperties properties = new DialogProperties();

        properties.extensions = new String[] {"xls"};
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(Environment.getExternalStorageDirectory().getPath());
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);

        FilePickerDialog filePickerDialog = new FilePickerDialog(this, properties);
        filePickerDialog.setTitle("Выберите файл");
        filePickerDialog.setDialogSelectionListener(this);
        filePickerDialog.show();

        /*Intent intent = new Intent(this, FilePickerActivity.class);

        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setShowImages(false)
                .setShowVideos(false)
                .setShowFiles(true)
                .setSuffixes("xls")
                .setMaxSelection(1)
                .setSingleChoiceMode(true)

        .build());

        startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*if (requestCode == REQUEST_CODE_FILE_PICKER) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if (files != null) {
                String path = files.get(0).getPath();
                mPresenter.importFile(path);
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_import:
                newPurchase();
                return true;
            case R.id.action_sort:
                sort();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sort() {
        String[] list = {"По возрастанию", "По убыванию", "Отмеченные", "Не отмеченные"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SORTING_TYPE sorting_type = SORTING_TYPE.values()[which];
                mPresenter.setupAdapter(sorting_type);
            }
        }).create().show();

    }

    @Override
    public void setupAdapter(List<Product> products) {
        if (mAdapter == null) {
            mAdapter = new PurchaseAdapter(this, mPresenter, products);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setItems(products);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSelectedFilePaths(String[] files) {
        mPresenter.importFile(files[0]);
    }
}
