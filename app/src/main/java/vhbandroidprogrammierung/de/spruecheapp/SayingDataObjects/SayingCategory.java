package vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects;

import java.util.Comparator;

/**
 * Created by Patrick PC on 12.08.2016.
 */
public class SayingCategory {

    private String categoryName;

    public SayingCategory(String s) {
        this.categoryName = s;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Comparator<SayingCategory> CompareByCategory = new Comparator<SayingCategory>() {

        public int compare(SayingCategory s1, SayingCategory s2) {
            String category1 = s1.getCategoryName().toUpperCase();
            String category2 = s2.getCategoryName().toUpperCase();

            //Aufsteigend
            return category1.compareTo(category2);

            //Absteigend
            //return category1.compareTo(category2);
        }};
}
