package vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects;

import java.util.Comparator;

import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.ListItemSaying;

/**
 * Created by Patrick PC on 04.08.2016.
 */
public class Saying implements ListItemSaying {

    private String saying;
    private boolean isFavorite, isUserSaying;
    private SayingAuthor sayingAuthor;
    private SayingCategory sayingCategory;
    private int listPositionBeforeGettingRemoved;


    public Saying() {
        // empty
    }

    public Saying(String saying) {
        this.saying = saying;
    }

    public Saying(String saying, SayingAuthor author, SayingCategory category, boolean isFavorite, boolean isUserSaying) {
        this.saying = saying;
        this.sayingAuthor = author;
        this.sayingCategory = category;
        this.isFavorite = isFavorite;
        this.isUserSaying = isUserSaying;
    }

    public int getListPositionBeforeGettingRemoved() {
        return listPositionBeforeGettingRemoved;
    }

    public void setListPositionBeforeGettingRemoved(int listPositionBeforeGettingRemoved) {
        this.listPositionBeforeGettingRemoved = listPositionBeforeGettingRemoved;
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


    public static Comparator<Saying> CompareBySaying = new Comparator<Saying>() {

        public int compare(Saying s1, Saying s2) {
            String saying1 = s1.getSaying().toUpperCase();
            String saying2 = s2.getSaying().toUpperCase();

            //Aufsteigend
            return saying1.compareTo(saying2);

            //Absteigend
            //return saying1.compareTo(saying2);
        }};

    public static Comparator<Saying> CompareByCategory = new Comparator<Saying>() {

        public int compare(Saying s1, Saying s2) {
            String category1 = s1.getSayingCategory().getCategoryName().toUpperCase();
            String category2 = s2.getSayingCategory().getCategoryName().toUpperCase();

            //Aufsteigend
            return category1.compareTo(category2);

            //Absteigend
            //return category1.compareTo(category2);
        }};

    public static Comparator<Saying> CompareByAuthor = new Comparator<Saying>() {

        public int compare(Saying s1, Saying s2) {
            String author1 = s1.getSayingAuthor().getAuthorName().toUpperCase();
            String author2 = s2.getSayingAuthor().getAuthorName().toUpperCase();

            //Aufsteigend
            return author1.compareTo(author2);

            //Absteigend
            //return author1.compareTo(author2);
        }};

}