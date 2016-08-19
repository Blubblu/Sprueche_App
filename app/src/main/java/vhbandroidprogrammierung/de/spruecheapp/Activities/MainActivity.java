package vhbandroidprogrammierung.de.spruecheapp.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.FavFragment;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.HomeFragment;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.UserSayingsFragment;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.Saying;
import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.SayingAuthor;
import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.SayingCategory;

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
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisierungsmethoden

        initLists();
        initFragment();
        initToolbar();
        initDrawer();

    }


    private void initLists() {

        sayingList = new ArrayList<>();
        categoryList = new ArrayList<>();
        authorList = new ArrayList<>();

        loadTextFile();


    }


    public void sortLists(boolean saying, boolean categories, boolean authors) {
        if (sayingList != null && saying) {
            Collections.sort(sayingList, Saying.CompareByCategory);
            Log.i(TAG, "sortLists: sayingList sorted");
        }

        if (categoryList != null && categories) {
            Collections.sort(categoryList, SayingCategory.CompareByCategory);
            Log.i(TAG, "sortLists: categoryList sorted");
        }

        if (authorList != null && authors) {
            Collections.sort(authorList, SayingAuthor.CompareByAuthor);
            Log.i(TAG, "sortLists: categoryList sorted");
        }
    }

    private void initFragment() {
        homeFragment = new HomeFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView,homeFragment).commit();
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

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                title = getResources().getString(R.string.sayings);
                break;
            case R.id.nav_fav_sayings:
                fragment = new FavFragment();
                title = getResources().getString(R.string.fav_sayings);
                break;
            case R.id.nav_user_sayings:
                fragment = new UserSayingsFragment();
                title = getResources().getString(R.string.user_sayings);
                break;
            case R.id.nav_settings:
                toaster("Settings", true);
                break;
            case R.id.nav_about:
                toaster("About", true);
                break;
            default:
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, fragment);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle(title);
        }

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
     * @param pos
     * @param cv
     */
    public void favStarHasBeenClicked(int pos, CardView cv, ImageView imageView) {

        boolean newFavState = !sayingList.get(pos).isFavorite();

        sayingList.get(pos).setFavorite(newFavState);

        if(homeFragment != null) {
            homeFragment.notifyNestedFragmentsOfDataChange();
        }


        Log.i(TAG, "favStarHasBeenClicked: Fav has been clicked, is now: " + sayingList.get(pos).isFavorite() + "\n" + sayingList.get(pos).getSaying());

        if(newFavState) {
            imageView.setBackgroundResource(R.drawable.ic_star_grey600_36dp);
        } else {
            imageView.setBackgroundResource(R.drawable.ic_star_outline_grey600_36dp);
        }
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
     *
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
        if (sayingText.length() > 0 && sayingText.length() < 130) {
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


}


