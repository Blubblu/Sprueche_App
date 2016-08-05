package vhbandroidprogrammierung.de.spruecheapp;

import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.ListItemSaying;

/**
 * Created by Patrick PC on 04.08.2016.
 */
public class Saying implements ListItemSaying {

    private String saying, author, category;
    private boolean isFavorite, isUserSaying;

    public Saying() {
        // empty
    }

    public Saying(String saying, String author, String category, boolean isFavorite, boolean isUserSaying) {
        this.saying = saying;
        this.author = author;
        this.category = category;
        this.isFavorite = isFavorite;
        this.isUserSaying = isUserSaying;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isUserSaying() {
        return isUserSaying;
    }

    public void setUserSaying(boolean userSaying) {
        isUserSaying = userSaying;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }
}
