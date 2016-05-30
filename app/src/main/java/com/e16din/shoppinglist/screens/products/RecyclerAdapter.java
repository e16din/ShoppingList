package com.e16din.shoppinglist.screens.products;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.e16din.shoppinglist.R;
import com.e16din.shoppinglist.model.Product;
import com.e16din.simplerecycler.adapter.SimpleRecyclerAdapter;
import com.e16din.simplerecycler.adapter.SimpleViewHolder;

import org.joda.time.DateTime;

import java.util.List;

public class RecyclerAdapter<T extends Product>
        extends SimpleRecyclerAdapter<RecyclerAdapter.ViewHolder, T> {

    public RecyclerAdapter(@NonNull Context context, @NonNull List items) {
        super(context, items, R.layout.item_product);
    }

    @Override
    protected ViewHolder newViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);

        final Product item = getItem(position);

        holder.tvName.setText(item.getName());
        if (item.getChecked() != 0) {
            holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvCheckDate.setText(
                    new DateTime(item.getChecked()).toString("dd MMMM yyyy HH:mm"));
            holder.tvCheckDate.setVisibility(View.VISIBLE);
        } else {
            holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvCheckDate.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends SimpleViewHolder {
        TextView tvName;
        TextView tvCheckDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCheckDate = (TextView) itemView.findViewById(R.id.tvCheckDate);
        }
    }
}