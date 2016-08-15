package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.Activities.MainActivity;
import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewCreator;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.Saying;
import vhbandroidprogrammierung.de.spruecheapp.SayingAuthor;
import vhbandroidprogrammierung.de.spruecheapp.SayingCategory;

public class UserSayingsFragment extends Fragment implements View.OnClickListener {

    public static final int SNACKBAR_DURATION = 5000;
    private static final String USER_SAYING_CATEGORY = "Ich";
    private static final String TAG = "UserSayingsFragment";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ArrayList<Saying> userSayingArrayList;
    private ArrayList<Saying> tmpDeletedSayings;
    private RecyclerAdapter recyclerAdapter;
    private View view;
    private Context context;
    private List<String> categoryList;
    private MainActivity activity;
    private CoordinatorLayout coordinatorLayout;
    private int elementsDeleted;
    private Snackbar deleteSnackbar;
    private long snackBarStartTime;
    private Calendar calendar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_sayings, null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.own_sayings);
        this.context = getContext();
        this.activity = (MainActivity) getActivity();

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.cl_user_sayings);

        tmpDeletedSayings = new ArrayList<Saying>();

        initFab();
        initRecyclerView();
        initSwipeableRecyclerViewTouchListener(recyclerView);

        return view;

    }

    private void initFab() {
        this.fab = (FloatingActionButton) view.findViewById(R.id.fab_user_sayings);
        this.fab.setOnClickListener(this);
        animateFab();
    }


    /**
     * FAB nach einem kurzen Delay einblenden
     */
    private void animateFab() {

        fab.setVisibility(View.INVISIBLE);
        fab.setScaleX(0.0F);
        fab.setScaleY(0.0F);
        fab.setAlpha(0.0F);
        fab.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fab.getViewTreeObserver().removeOnPreDrawListener(this);
                fab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.show();
                    }
                }, Config.FAB_ANIMATION_TIME);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        //TODO FAB action
        createUserSayingDialog();
    }

    /**
     * Kümmert sich um Scroll-Events auf der RecyclerView um den FAB zu verstecken oder zeigen
     */
    private void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_user_sayings);
        userSayingArrayList = new ArrayList<>();

        fillSayingArray();

        recyclerAdapter = RecyclerViewCreator.buildRecyclerViewWithAdapter(recyclerView, userSayingArrayList, context);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void fillSayingArray() {

        userSayingArrayList.clear();
        for (Saying saying : activity.getSayingList()) {
            if (saying.getSayingAuthor().getAuthorName().equals(USER_SAYING_CATEGORY)) {
                userSayingArrayList.add(saying);
            }
        }
    }


    /**
     * Elmente im RecyclerView können nach links oder rechts weggewischt werden
     * TODO: Gelöschte Elemente müssen wirklich gelöscht werden, im Moment tauchen sie nach dem swipe wieder auf
     *
     * @param rv die RecyclerView
     */
    public void initSwipeableRecyclerViewTouchListener(RecyclerView rv) {

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(rv,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Log.i(TAG, "onDismissedBySwipeLeft: left swipe on " + position);
                                    deleteUserSaying(position);
                                }
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Log.i(TAG, "onDismissedBySwipeLeft: right swipe on " + position);
                                    deleteUserSaying(position);
                                }
                            }
                        });

        recyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    /**
     * Nachdem User einen Spruch weggewischt hat, muss er aus der Liste gelöscht werden
     * Den gelöschten Spruch in der Hauptliste der MainActivity suchen, dort löschen und hier aktualisieren
     * <p/>
     * Wenn ein Element gelöscht wird, erscheint die Snackbar, die die Rückgängig-Aktion anbietet. Diese Snackbar wird für 5 Sekunden gezeigt.
     * Wenn während dieser 5 Sekunden noch ein Element gelöscht wird, wird die Snackbar erneuert und ihr Text ändert sich (+1).
     * So einen Anzeigezeitraum nennen wir "Snackbar-Periode"
     * <p/>
     * Gelöschte Elemente werden einem temporärem Array hinzugefügt.
     * Wenn gerade keine Snackbar-Periode ist, wird dieses Array gellert (um die Elemente der vorherigen Periode zu beseitigen)
     * Die Variable elementsDeleted zählt mit, wie viele Elemente gerade in dieser Periode sind.
     * <p/>
     * Über die Calendar Objekte und deren Zeit in Millisekunden wird überprüft, ob gerade eine Periode ist.
     *
     * @param position
     */
    private void deleteUserSaying(int position) {

        // Vorbereitung zur Überprüfung einer Snackbar-Periode
        calendar = Calendar.getInstance();
        long currentSystemTime = calendar.getTimeInMillis();

        // Wenn gerade keine Periode ist, das tmp Array leeren und den Counter zurücksetzen
        long timeDiff = currentSystemTime - snackBarStartTime;
        if (snackBarStartTime != 0 && timeDiff >= 5000) {
            Log.i(TAG, "deleteUserSaying: snackbar period exceeded: " + timeDiff);

            tmpDeletedSayings.clear();
            elementsDeleted = 0;
        } /*else {
            Log.i(TAG, "deleteUserSaying: elements ind tmpDeletedSayings");
            for(Saying s : tmpDeletedSayings) {
                Log.i(TAG, "deleteUserSaying: " + s.getSaying());
            }
        }*/

        // Zu löschendes Element
        Saying deletedSaying = userSayingArrayList.get(position);

       /* // In dem zu löschenden Element die ehemalige Position in der Liste speichern
        deletedSaying.setListPositionBeforeGettingRemoved(position);*/

        /*
         Alle Sprüche nach dem gelöschten durchsuchen und daraus entfernen.
         Vorher noch in dem tmp Array speichern und die Counter erhöhen
         Snackbar zeigen
          */
        for (int i = 0; i < activity.getSayingList().size(); i++) {
            if (activity.getSayingList().get(i) == deletedSaying) {
                tmpDeletedSayings.add(deletedSaying);
                elementsDeleted++;
                activity.getSayingList().remove(i);
                showDeleteSnackbar();
            }
        }

        // Liste aktualisieren
        updateSayingList();
    }

    private void updateSayingList() {
        fillSayingArray();
        recyclerAdapter.notifyDataSetChanged();
    }

    /*
    elementsDeleted gibt an, wie viele Elemente in der aktuellen Snackbar-Periode sind. Dementsprechend den Text anpassen.
     */
    private void showDeleteSnackbar() {

        String snackbarText = "";

        if (elementsDeleted == 1) {
            snackbarText = "1 Spruch gelöscht";
        } else if (elementsDeleted > 1) {
            snackbarText = elementsDeleted + " Sprüche gelöscht";
        }

        deleteSnackbar = Snackbar
                .make(coordinatorLayout, snackbarText, 5000).setAction("Rückgängig", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        undoRemoveSayings();
                    }
                });
        deleteSnackbar.show();

        // Die Snackbar-Startzeit aktualisieren
        calendar = Calendar.getInstance();
        snackBarStartTime = calendar.getTimeInMillis();
    }

    private void undoRemoveSayings() {

        // for (int i = tmpDeletedSayings.size() - 1; i >= 0; i--) {
        for (int i = 0; i < tmpDeletedSayings.size(); i++) {

            // activity.getSayingList().add(tmpDeletedSayings.get(i).getListPositionBeforeGettingRemoved(), tmpDeletedSayings.get(i));
            activity.getSayingList().add(tmpDeletedSayings.get(i));
        }

        // tmp Liste leeren, wenn die Elemente wieder in die Hauptliste gefügt wurden und diese aktualisieren
        tmpDeletedSayings.clear();
        updateSayingList();
    }

    public void createUserSayingDialog() {

        final View layout = LayoutInflater.from(context).inflate(R.layout.user_saying_dialog, null);

        final TextInputLayout sayingName = (TextInputLayout) layout.findViewById(R.id.til_saying);
        final TextInputLayout newCategoryName = (TextInputLayout) layout.findViewById(R.id.til_new_category);
        final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner_dialog);

        initDialogSpinner(layout, spinner, newCategoryName);

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Neuer Spruch");
        dialog.setView(layout);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                String saying = sayingName.getEditText().getText().toString();
                String newCategory = newCategoryName.getEditText().getText().toString();
                boolean newCategorySelected = newCategoryName.getVisibility() == View.VISIBLE;
                int spinnerSelectionIndex = spinner.getSelectedItemPosition();
                String spinnerSelectedCategoryText = spinner.getSelectedItem().toString();

                if ((!saying.equals("") && ((newCategorySelected && !newCategory.equals("")) || !newCategorySelected))) {
                    Log.i(TAG, "onClick: Dialog setPositiveButton conditions valid");

                    Saying newSaying = new Saying(saying);
                    newSaying.setSayingAuthor(new SayingAuthor(USER_SAYING_CATEGORY));

                    if (newCategorySelected) {
                        newSaying.setSayingCategory(new SayingCategory(newCategory));
                    } else {
                        SayingCategory tmpCategory;

                        for (int i = 0; i < activity.getCategoryList().size(); i++) {
                            if (spinnerSelectedCategoryText.equals(activity.getCategoryList().get(i).getCategoryName())) {
                                newSaying.setSayingCategory(activity.getCategoryList().get(i));
                            }
                        }
                    }

                    Log.i(TAG, "onClick: new" +
                            " Saying: " + newSaying.getSaying() +
                            " Category: " + newSaying.getSayingCategory().getCategoryName() +
                            " Author: " + newSaying.getSayingAuthor().getAuthorName());
                    activity.getSayingList().add(newSaying);
                    updateSayingList();
                }
            }


        }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


            }
        }).create();
        dialog.show();
    }

    private void initDialogSpinner(View layout, Spinner spinner, final TextInputLayout tip) {

        //TODO: von MainActivity alle verfügbaren SayingCategory-Objekte holen

        // Erst alle Kategorie Namen aus der MainActivity holen...
        this.categoryList = new ArrayList<String>();
        for (SayingCategory category : activity.getCategoryList()) {
            categoryList.add(category.getCategoryName());
        }

        //... dann diese Namen in die FINALE Liste für den Spinner einfügen...
        final List<String> spinnerArray = new ArrayList<String>();
        for (String categoryName : categoryList) {
            spinnerArray.add(categoryName);
        }

        // ...am Ende die Möglichkeit, eine neue Kategorie zu anzulegen
        spinnerArray.add("+ NEUE KATEGORIE");


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /*
            Herausfinden, welches item ausgewählt wurde. Wenn es das letzte ist (Neue Kategorie), TODO: ...
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // TextInputLayout für neue Kategorie sichtbar machen, wenn "Neue Kategorie" ausgewählt wurde
                if (i == spinnerArray.size() - 1) {
                    tip.setVisibility(View.VISIBLE);
                    tip.requestFocus();

                } else {
                    tip.setVisibility(View.GONE);
                    tip.clearFocus();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}


