package com.e16din.shoppinglist.screens.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.e16din.alertmanager.AlertDialogCallback;
import com.e16din.alertmanager.AlertManager;
import com.e16din.intentmaster.IntentMaster;
import com.e16din.shoppinglist.R;
import com.e16din.shoppinglist.model.Product;
import com.e16din.shoppinglist.model.Product_Table;
import com.e16din.shoppinglist.model.ShoppingList;
import com.e16din.shoppinglist.screens.base.BaseActivity;
import com.e16din.simplerecycler.adapter.SimpleRecyclerAdapter;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import org.joda.time.DateTime;

import java.util.List;

public class ProductsActivity extends BaseActivity {

    private RecyclerAdapter mAdapter;

    private ShoppingList mShoppingList;

    private FloatingActionButton vFab;
    private RecyclerView vRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        mShoppingList = (ShoppingList) IntentMaster.getExtra(this);

        vFab = (FloatingActionButton) findViewById(R.id.vFab);
        vRecycler = (RecyclerView) findViewById(R.id.vRecycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAdapter == null) {
            final List<Product> products = mShoppingList.getProducts();
            mAdapter = new RecyclerAdapter(this, products);
            mAdapter.setOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener<Product>() {
                @Override
                public void onClick(Product item, int position) {
                    item.setChecked(item.getChecked() != 0 ? 0 : DateTime.now().getMillis());

                    mAdapter.notifyDataSetChanged();
                    item.update();
                }
            });
            vRecycler.setAdapter(mAdapter);

            vFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertManager.manager(ProductsActivity.this).showSingleLineMessageEditor(
                            getString(R.string.hint_product_name), "",
                            new AlertDialogCallback<String>() {
                                @Override
                                public void onPositive(String... strings) {
                                    addProduct(strings[0]);
                                }
                            });
                }
            });
        } else {
            SQLite.select().from(Product.class).where(Product_Table.ownerId.is(mShoppingList.getId()))
                    .async().queryResultCallback(
                    new QueryTransaction.QueryResultCallback<Product>() {
                        @Override
                        public void onQueryResult(QueryTransaction transaction,
                                                  @NonNull CursorResult<Product> tResult) {
                            final List<Product> products = tResult.toList();

                            mAdapter.clearAll();
                            mAdapter.addAll(0, products);
                        }
                    });
        }
    }

    protected void addProduct(String name) {
        addProduct(name, 0);
    }

    protected void addProduct(String name, long checked) {
        Product item = new Product(name, DateTime.now().getMillis());
        item.setOwnerId(mShoppingList.getId());
        if (checked != 0) {
            item.setChecked(checked);
        }
        mAdapter.add(item);
        item.insert();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setTitle("Список [" + mShoppingList.getName() + "]");
    }
}
