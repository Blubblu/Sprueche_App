package vhbandroidprogrammierung.de.spruecheapp.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.FavFragment;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.HomeFragment;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.UserSayingsFragment;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.Saying;
import vhbandroidprogrammierung.de.spruecheapp.SayingAuthor;
import vhbandroidprogrammierung.de.spruecheapp.SayingCategory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Saying> sayingList;
    private List<SayingCategory> categoryList;
    private List<SayingAuthor> authorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisierungsmethoden

        initLists();
        initFragment();
        initDrawer();
        initToolbar();
    }


    private void initLists() {

        sayingList = new ArrayList<>();
        categoryList = new ArrayList<>();
        authorList = new ArrayList<>();

        loadTextFile();


        /*
                //TODO: Nur für Testzwecke
            categoryList.add(new SayingCategory("Lebensweisheiten"));
            categoryList.add(new SayingCategory("Liebe"));
            categoryList.add(new SayingCategory("Motivation"));
            categoryList.add(new SayingCategory("Politik"));

            authorList.add(new SayingAuthor("Ghandi"));
            authorList.add(new SayingAuthor("Dwight D. Eisenhower"));
            authorList.add(new SayingAuthor("unbekannt"));
            authorList.add(new SayingAuthor("Angela Merkel"));
            authorList.add(new SayingAuthor("Ich"));

            sayingList.add(new Saying("Zuerst ignorieren sie dich, dann lachen sie über dich, dann bekämpfen " +
                                              "sie dich und dann gewinnst du.", authorList.get(0), categoryList.get(0), false, false));
            sayingList.add(new Saying("Der Schwache kann nicht verzeihen. Verzeihen ist eine Eigenschaft des Starken.",
                                      authorList.get(0), categoryList.get(0), false, false));
            sayingList.add(new Saying("Wir schaffen das", authorList.get(3), categoryList.get(3), true, false));
            sayingList.add(new Saying("Ich war dagegen aus zwei Gründen. Erstens waren die Japaner bereit sich zu " +
                                              "ergeben, und es war nicht notwendig, sie mit dieser schrecklichen Sache zu treffen. Und Zweitens, " +
                                              "ich hasste den Gedanken, dass unser Land das erste sein würde, das solch eine Waffe einsetzt.",
                                      authorList.get(1), categoryList.get(3), true, false));
            sayingList.add(new Saying("Es gibt immer absolut immer einen Grund dankbar zu sein - finde ihn!", authorList.get(2), categoryList.get(2), false, false));
            sayingList.add(new Saying("Nur wer sich selbst liebt kann auch andere lieben.", authorList.get(2), categoryList.get(1), false, false));


            sayingList.add(new Saying("Testspruch für Eigene Sprüche", authorList.get(4), categoryList.get(1), false, true));
            sayingList.add(new Saying("Testspruch 2 für Eigene Sprüche", authorList.get(4), categoryList.get(1), false, true));
            sayingList.add(new Saying("Testspruch 3 für Eigene Sprüche", authorList.get(4), categoryList.get(1), false, true));
            sayingList.add(new Saying("Testspruch 4 für Eigene Sprüche", authorList.get(4), categoryList.get(1), false, true));
            sayingList.add(new Saying("Testspruch 5 für Eigene Sprüche", authorList.get(4), categoryList.get(1), false, true));*/
    }


    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    /**
     * Startet das Fragment, des geklickten NavigationDrawer Elements.
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();

        // Alle außer home auf den backStack legen um durch die Zurück-Taste auf der Hauptseite zu landen
        switch (item.getItemId()) {
            case R.id.nav_home:
                FragmentTransaction homeFragmentTransaction = fragmentManager.beginTransaction();
                homeFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                break;
            case R.id.nav_fav_sayings:
                FragmentTransaction favFragmentTransaction = fragmentManager.beginTransaction().addToBackStack("fragback");
                favFragmentTransaction.replace(R.id.containerView, new FavFragment()).commit();
                break;
            case R.id.nav_user_sayings:
                FragmentTransaction userFragmentTransaction = fragmentManager.beginTransaction().addToBackStack("fragback");
                userFragmentTransaction.replace(R.id.containerView, new UserSayingsFragment()).commit();
                break;
            case R.id.nav_settings:
                toaster("Settings", true);
                break;
            case R.id.nav_about:
                toaster("About", true);
                break;
            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Gibt eine Toast-Nachricht aus.
     * "true" übergeben, wenn der Toast lang sein soll, "false" fr kurz
     *
     * @param text, lengthLong
     */
    public void toaster(String text, boolean lengthLong) {

        int length = lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(MainActivity.this, text, length).show();
    }

    /**
     * Wird aufgerufen, wenn der Stern eines RecyclerView Elements in irgend einem Fragment geklickt wurde.
     * TODO: Den "Favorit" Datenbankeintrag für den geklickten Spruch ändern.
     *
     * @param pos
     * @param cv
     */
    public void favStarHasBeenClicked(int pos, CardView cv) {

    }


    /**
     * GETTERS & SETTERS
     *
     * @return
     */

    public List<Saying> getSayingList() {
        return sayingList;
    }

    public void setSayingList(List<Saying> sayingList) {
        this.sayingList = sayingList;
    }

    public List<SayingCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SayingCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SayingAuthor> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<SayingAuthor> authorList) {
        this.authorList = authorList;
    }

    protected void loadTextFile() {
        try {

            /*
            Datei aus assets in InputStreamReader laden (UTF-8 encoding). Damit den BufferedReader initialisieren.
            Der BufferedReader liest Zeile für Zeile ein.
             */
            InputStreamReader reader = new InputStreamReader(getAssets().open("zitate.txt"), "UTF-8");
            BufferedReader br = new BufferedReader(reader);

            // gibt an, wenn ein Zitatende erreicht wurde und zum Nächsten iteriert werden soll
            boolean keepIteratingToNextLine = false;

            String line = "";
            List<String> tmpSaying = new ArrayList<>();

            while ((line = br.readLine()) != null) {

                // Leere Zeilen berspringen
                if (!line.equals("")) {
                    keepIteratingToNextLine = false;
                }

                // Nur Datein einleisen, wenn etwas in der Zeile steht.
                if (!keepIteratingToNextLine) {

                    // Alle Zeilen des Spruchs in der String Liste ablegen
                    if (!line.equals("")) {
                        tmpSaying.add(line);
                    } else {
                        // Wenn leere Zeile erreicht ist, wird der Spruch verarbeitet
                        keepIteratingToNextLine = true;
                        processTmpSaying(tmpSaying);
                        tmpSaying.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Die eingelesenen Zeilen eines Spruchs für unsere Datenhaltung bearbeiten
     * Unnötige Zeilen entfernen
     * Autor & Kategorie in Objekte verwandeln
     * @param tmpSaying
     */
    private void processTmpSaying(List<String> tmpSaying) {

        Saying newSaying = new Saying();
        int indexLast = tmpSaying.size() - 1;


        // Falls vorhanden, die Jahreszahl löschen
        if (tmpSaying.get(indexLast).contains("Jahr:")) {
            tmpSaying.remove(indexLast);
            indexLast--;

            Log.i(TAG, "STRINGSTOSAYING: 1");
        }

        /*
        Author Behandlung
        Schauen, ob es den Author schon gibt, ansonsten erstellen
        Dem Saying zuordnen
         */
        String author = tmpSaying.get(indexLast).replaceAll(Config.AUHTOR_REPLACE_STRING, "");
        int exisitingAuthorIndex = getExistingAuthorIndex(author);

        if (exisitingAuthorIndex == -1) {
            SayingAuthor sa = new SayingAuthor(author);
            authorList.add(sa);
            newSaying.setSayingAuthor(sa);
            Log.i(TAG, "STRINGSTOSAYING: 2.1");
        } else {
            newSaying.setSayingAuthor(authorList.get(exisitingAuthorIndex));
            Log.i(TAG, "STRINGSTOSAYING: 2.2");
        }
        tmpSaying.remove(indexLast);
        indexLast--;

        /*
        Kategorie Behandlung
        Schauen, ob es die Kategorie schon gibt, ansonsten erstellen
        Dem Saying zuordnen
         */
        String category = tmpSaying.get(indexLast).replaceAll(Config.CATEGORY_REPLACE_STRING, "");
        ;
        int exisitingCategoryIndex = getExistingCaegoryIndex(category);

        if (exisitingCategoryIndex == -1) {
            SayingCategory sc = new SayingCategory(category);
            categoryList.add(sc);
            newSaying.setSayingCategory(sc);
            Log.i(TAG, "STRINGSTOSAYING: 3.1 " + category);
        } else {
            newSaying.setSayingCategory(categoryList.get(exisitingCategoryIndex));
            Log.i(TAG, "STRINGSTOSAYING: 3.2 " + category);
        }
        tmpSaying.remove(indexLast);
        indexLast--;


    /*    if (tmpSaying.size() == 2) {
            tmpSaying.remove(indexLast);
            newSaying.setSaying(tmpSaying.get(0));
            sayingList.add(newSaying);
            Log.i(TAG, "STRINGSTOSAYING: 4");
            indexLast--;
        } else if (tmpSaying.size() > 2) {

           *//*
           Letzte und vorletzte Zeile sind unerwünscht
            *//*
            tmpSaying.remove(indexLast);
            tmpSaying.remove(indexLast - 1);
            indexLast--;
            indexLast--;


            // Falls noch eine Zeile mit (xy) vorhanden ist
            if (tmpSaying.get(indexLast).charAt(0) == '(' && tmpSaying.get(indexLast).contains(")")) {
                tmpSaying.remove(indexLast);
                indexLast--;
                Log.i(TAG, "STRINGSTOSAYING: 5");
            }

            String sayingText = "";
            for (int i = 0; i < tmpSaying.size() - 1; i++) {
                sayingText += tmpSaying.get(i) + " ";
            }

            newSaying.setSaying(sayingText);
            sayingList.add(newSaying);
            Log.i(TAG, "STRINGSTOSAYING: 6");
        } else {
            Log.i(TAG, "STRINGSTOSAYING: ABORT");
            return;

            // tmpSaying nicht verarbeiten, da nicht damit umgegangen werden kann
        }*/

        tmpSaying.remove(indexLast);
        indexLast--;
        tmpSaying.remove(indexLast);
        indexLast--;


        for (int i = 0; i < tmpSaying.size(); i++) {
            if (tmpSaying.get(i).contains("(")) {
                Log.i(TAG, "processTmpSaying: TREFFER " + tmpSaying.get(i));
                tmpSaying.remove(i);
                indexLast--;
            }
        }

        String sayingText = "";
        for (String s : tmpSaying) {
            sayingText += s + " ";
        }

        // leere oder zu lange Sprüche nicht speichern
        if (sayingText.length() > 0  && sayingText.length() < 130) {
            newSaying.setSaying(sayingText);
            sayingList.add(newSaying);
        }
    }

    /**
     * Schaut, ob es schon ein Kategorie Objekt mit diesem Namen gibt und gibt dessen index in der Liste zurück
     *
     * @param category Name der Kategorie
     * @return index in der Liste, oder -1 falls es das Objekt noch nicht gibt
     */
    private int getExistingCaegoryIndex(String category) {
        int resultIndex = -1;

        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryName().equals(category)) {
                resultIndex = i;
                return resultIndex;
            }
        }
        return resultIndex;
    }

    /**
     * Schaut, ob es schon ein Author Objekt mit diesem Namen gibt und gibt dessen index in der Liste zurück
     *
     * @param author Name des Authors
     * @return index in der Liste, oder -1 falls es das Objekt noch nicht gibt
     */
    private int getExistingAuthorIndex(String author) {

        int resultIndex = -1;

        for (int i = 0; i < authorList.size(); i++) {
            if (authorList.get(i).getAuthorName().equals(author)) {
                resultIndex = i;
                return resultIndex;
            }
        }
        return resultIndex;
    }
}


