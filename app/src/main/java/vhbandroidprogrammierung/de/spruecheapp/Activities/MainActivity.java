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
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        initToolbar();
        initFragment();
        initDrawer();
    }

    private void initLists() {

        sayingList = new ArrayList<>();
        categoryList = new ArrayList<>();
        authorList = new ArrayList<>();

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
        sayingList.add(new Saying("Testspruch 5 für Eigene Sprüche", authorList.get(4), categoryList.get(1), false, true));
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


