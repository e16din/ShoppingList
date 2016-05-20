package com.e16din.shoppinglist.screens.shopping;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.e16din.alertmanager.AlertDialogCallback;
import com.e16din.alertmanager.AlertManager;
import com.e16din.datamanager.DataManager;
import com.e16din.intentmaster.IntentMaster;
import com.e16din.shoppinglist.R;
import com.e16din.shoppinglist.TheApplication;
import com.e16din.shoppinglist.model.ShoppingList;
import com.e16din.shoppinglist.screens.base.BaseActivity;
import com.e16din.shoppinglist.screens.products.ProductsActivity;
import com.e16din.simplerecycleradapter.SimpleRecyclerAdapter;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import org.joda.time.DateTime;

import java.util.List;

public class ShoppingActivity extends BaseActivity {

    private RecyclerAdapter mAdapter;
    private RecyclerView vRecycler;

    private FloatingActionButton vFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        vFab = (FloatingActionButton) findViewById(R.id.vFab);
        vRecycler = (RecyclerView) findViewById(R.id.vRecycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.title_shopping));
    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLite.select().from(ShoppingList.class).async()
                .queryResultCallback(new QueryTransaction.QueryResultCallback<ShoppingList>() {
                    @Override
                    public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<ShoppingList> tResult) {
                        final List<ShoppingList> shoppingLists = tResult.toList();

                        if (mAdapter == null) {
                            mAdapter = new RecyclerAdapter(ShoppingActivity.this, shoppingLists);
                            mAdapter.setOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener<ShoppingList>() {
                                @Override
                                public void onClick(ShoppingList item, int position) {
                                    IntentMaster.startActivity(ShoppingActivity.this, ProductsActivity.class,
                                            item);
                                }
                            });
                            vRecycler.setAdapter(mAdapter);

                            //add item for example
                            if (DataManager.getInstance().loadBool(TheApplication.KEY_NEED_EXAMPLE)) {
                                addShoppingList("Продукты");
                            }

                            vFab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertManager.manager(ShoppingActivity.this).showSingleLineMessageEditor(
                                            getString(R.string.hint_list_name), "",
                                            new AlertDialogCallback<String>() {
                                                @Override
                                                public void onPositive(String... strings) {
                                                    addShoppingList(strings[0]);
                                                }
                                            });
                                }
                            });
                        } else {
                            mAdapter.clear();
                            mAdapter.addAll(0, shoppingLists);
                        }
                    }
                }).execute();
    }

    private void addShoppingList(String name) {
        ShoppingList item = new ShoppingList(name, DateTime.now().getMillis());
        mAdapter.add(item);
        item.insert();
    }
}
