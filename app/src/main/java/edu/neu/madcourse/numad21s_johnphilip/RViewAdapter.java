package edu.neu.madcourse.numad21s_johnphilip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {

    private final List<ItemCard> itemList;
    private ItemClickListener listener;

    // Constructor
    public RViewAdapter(List<ItemCard> itemList) {
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_card, parent, false);
        return new RViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        ItemCard currentItem = itemList.get(position);

        holder.linkName.setText(currentItem.getLinkName());
        holder.linkURL.setText(currentItem.getLinkURL());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
