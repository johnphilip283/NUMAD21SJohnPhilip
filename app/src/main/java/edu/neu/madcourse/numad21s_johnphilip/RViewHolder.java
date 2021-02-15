package edu.neu.madcourse.numad21s_johnphilip;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RViewHolder extends RecyclerView.ViewHolder {

    public TextView linkName;
    public TextView linkURL;

    public RViewHolder(@NonNull View itemView, final LinkClickListener listener) {
        super(itemView);

        linkName = itemView.findViewById(R.id.item_link_name);
        linkURL = itemView.findViewById(R.id.item_link_url);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onLinkClick(linkURL.getText().toString());
                    }
                }
            }
        });
    }
}
