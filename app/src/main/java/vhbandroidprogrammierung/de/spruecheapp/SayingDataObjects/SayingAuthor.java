package vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects;

import java.util.Comparator;

/**
 * Created by Patrick PC on 12.08.2016.
 */
public class SayingAuthor {

    private String authorName;

    public SayingAuthor(String s) {
        this.authorName = s;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public static Comparator<SayingAuthor> CompareByAuthor = new Comparator<SayingAuthor>() {

        public int compare(SayingAuthor s1, SayingAuthor s2) {
            String author1 = s1.getAuthorName().toUpperCase();
            String author2 = s2.getAuthorName().toUpperCase();

            //Aufsteigend
            return author1.compareTo(author2);

            //Absteigend
            //return category1.compareTo(category2);
        }};
}
