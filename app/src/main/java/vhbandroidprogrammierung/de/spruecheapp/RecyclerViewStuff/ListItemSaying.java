package vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff;


public interface ListItemSaying {

    static final String TAG = "ListItemSaying";


    /**
     * GETTERS ++++++++++++++++++++++++++++++++++
     */
    String getSaying();

    String getAuthor();

    String getCategory();

    boolean isFavorite();

    boolean isUserSaying();

    /**
     * SETTERS ++++++++++++++++++++++++++++++++++
     */

    void setSaying(String saying);

    void setAuthor(String author);

    void setCategory(String category);

    void setFavorite(boolean isFavorite);

    void setUserSaying(boolean isUserSaying);
}