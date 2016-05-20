package com.e16din.shoppinglist.model;

import android.support.annotation.Nullable;

import com.e16din.shoppinglist.model.db.ShoppingListDb;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

@ModelContainer
@Table(database = ShoppingListDb.class)
public class ShoppingList extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name;

    @Column
    long created;

    @Nullable
    List<Product> products;

    public ShoppingList() {
    }

    public ShoppingList(String name, long created) {
        this.name = name;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "products")
    public List<Product> getProducts() {
        if (products == null || products.isEmpty()) {
            products = SQLite.select()
                    .from(Product.class)
                    .where(Product_Table.ownerId.eq(id))
                    .queryList();
        }
        return products;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
