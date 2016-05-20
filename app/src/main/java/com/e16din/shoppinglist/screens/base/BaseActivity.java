package com.e16din.shoppinglist.screens.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.e16din.shoppinglist.R;


public abstract class BaseActivity extends AppCompatActivity {

    public static boolean tryThis(Runnable runnable) {
        try {
            if (runnable != null) {
                runnable.run();
                return true;
            }
        } catch (NullPointerException | WindowManager.BadTokenException | IllegalStateException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        Toolbar toolbar = getToolbar();
        if (toolbar == null) return;

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        Toolbar mToolbar = getToolbar();
        if (mToolbar == null) return;

        if (!isTaskRoot()) {
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(getTitle());
    }
}
