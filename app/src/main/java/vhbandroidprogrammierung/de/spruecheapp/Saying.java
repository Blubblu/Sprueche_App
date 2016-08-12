package vhbandroidprogrammierung.de.spruecheapp;

import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.ListItemSaying;

/**
 * Created by Patrick PC on 04.08.2016.
 */
public class Saying implements ListItemSaying {

    private String saying;
    private boolean isFavorite, isUserSaying;
    private SayingAuthor sayingAuthor;
    private SayingCategory sayingCategory;

    public Saying() {
        // empty
    }

    public Saying(String saying, SayingAuthor author, SayingCategory category, boolean isFavorite, boolean isUserSaying) {
        this.saying = saying;
        this.sayingAuthor = author;
        this.sayingCategory = category;
        this.isFavorite = isFavorite;
        this.isUserSaying = isUserSaying;
    }

    public SayingAuthor getSayingAuthor() {
        return sayingAuthor;
    }

    public void setSayingAuthor(SayingAuthor sayingAuthor) {
        this.sayingAuthor = sayingAuthor;
    }

    public SayingCategory getSayingCategory() {
        return sayingCategory;
    }

    public void setSayingCategory(SayingCategory sayingCategory) {
        this.sayingCategory = sayingCategory;
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
