package vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff;


import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.SayingAuthor;
import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.SayingCategory;

public interface ListItemSaying {

    static final String TAG = "ListItemSaying";


    /**
     * GETTERS ++++++++++++++++++++++++++++++++++
     */
    String getSaying();

    SayingAuthor getSayingAuthor();

    SayingCategory getSayingCategory();

    boolean isFavorite();

    boolean isUserSaying();

    /**
     * SETTERS ++++++++++++++++++++++++++++++++++
     */

    void setSaying(String saying);

    void setSayingAuthor(SayingAuthor author);

    void setSayingCategory(SayingCategory category);

    void setFavorite(boolean isFavorite);

    void setUserSaying(boolean isUserSaying);

}