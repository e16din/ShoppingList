package com.e16din.shoppinglist;

import android.support.multidex.MultiDexApplication;

import com.e16din.datamanager.DataManager;
import com.e16din.lightutils.LightUtils;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;


public class TheApplication extends MultiDexApplication {

    public static final String KEY_LAUNCH_COUNTER = "LAUNCH_COUNTER";
    public static final String KEY_NEED_EXAMPLE = "NEED_EXAMPLE";

    @Override
    public void onCreate() {
        super.onCreate();
        LightUtils.init(this);
        DataManager.init(this);

        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true)
                .build());

        final int count = DataManager.getInstance().loadInt(KEY_LAUNCH_COUNTER) + 1;
        //count + 1 because loadInt first return -1
        DataManager.getInstance().save(KEY_LAUNCH_COUNTER, count == 0 ? count + 1 : count);

        if (isFirstStart()) {
            DataManager.getInstance().save(KEY_NEED_EXAMPLE, true);
        }
    }

    public static boolean isFirstStart() {
        return DataManager.getInstance().loadInt(KEY_LAUNCH_COUNTER) == 1;
    }
}
