package edu.neu.madcourse.numad21s_johnphilip;

public class ItemCard {

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

}
