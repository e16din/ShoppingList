package com.e16din.shoppinglist.model;


import com.e16din.shoppinglist.model.db.ShoppingListDb;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@ModelContainer
@Table(database = ShoppingListDb.class)
public class Product extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String name;

    @Column
    long checked;

    @Column
    long created;

    @Column
    long ownerId;

    public Product() {
    }

    public Product(String name, long created) {
        this.name = name;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getChecked() {
        return checked;
    }

    public void setChecked(long checked) {
        this.checked = checked;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
