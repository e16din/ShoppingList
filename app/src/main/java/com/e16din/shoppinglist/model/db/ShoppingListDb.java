package com.e16din.shoppinglist.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = ShoppingListDb.NAME, version = ShoppingListDb.VERSION)
public class ShoppingListDb {
    public static final String NAME = "ShoppingListDb";
    public static final int VERSION = 1;
}