package edu.neu.madcourse.numad21s_johnphilip;

import android.util.Log;

public class ItemCard implements ItemClickListener {

    private String linkName;
    private String linkURL;

    public ItemCard(String linkName, String linkURL) {
        this.linkName = linkName;
        this.linkURL = linkURL;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void onItemClick(int position) {
        Log.v("ItemCard", "Clicked");
    }

}
