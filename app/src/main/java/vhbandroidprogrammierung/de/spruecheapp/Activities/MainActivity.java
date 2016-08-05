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

import vhbandroidprogrammierung.de.spruecheapp.Fragments.FavFragment;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.HomeFragment;
import vhbandroidprogrammierung.de.spruecheapp.Fragments.UserSayingsFragment;
import vhbandroidprogrammierung.de.spruecheapp.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initFragment();
        initDrawer();
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }


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
                toaster("Settings");
                break;
            case R.id.nav_about:
                toaster("About");
                break;
            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void toaster(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void favStarHasBeenClicked(int pos, CardView cv) {

    }

}


