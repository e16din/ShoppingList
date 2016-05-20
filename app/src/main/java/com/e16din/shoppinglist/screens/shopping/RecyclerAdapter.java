package com.e16din.shoppinglist.screens.shopping;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.e16din.simplerecycleradapter.SimpleRecyclerAdapter;
import com.e16din.simplerecycleradapter.SimpleViewHolder;
import com.e16din.shoppinglist.R;
import com.e16din.shoppinglist.model.ShoppingList;

import java.util.List;

public class RecyclerAdapter<T extends ShoppingList>
        extends SimpleRecyclerAdapter<RecyclerAdapter.ViewHolder, T> {

    public RecyclerAdapter(@NonNull Context context, @NonNull List<T> items) {
        super(context, items, R.layout.item_shopping_list);
    }

    @Override
    protected ViewHolder newViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);

        final ShoppingList item = getItems().get(position);

        holder.tvName.setText(item.getName());
    }

    public static class ViewHolder extends SimpleViewHolder {
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}