package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewCreator;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.Saying;
import vhbandroidprogrammierung.de.spruecheapp.SayingAuthor;
import vhbandroidprogrammierung.de.spruecheapp.SayingCategory;

public class UserSayingsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "UserSayingsFragment";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ArrayList<Saying> userSayingArrayList;
    private RecyclerAdapter recyclerAdapter;
    private View view;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_sayings, null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.own_sayings);
        this.context = getContext();

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
                }, Config.fabAnimationTimeMS);
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

        buildDemoSayings();

        recyclerAdapter = RecyclerViewCreator.buildRecyclerViewWithAdapter(recyclerView, userSayingArrayList, context);
        recyclerView.setAdapter(recyclerAdapter);
    }

    // TODO nur zu Demo-Zwecken
    private void buildDemoSayings() {
        userSayingArrayList.add(new Saying("AAA Glaube an Wunder, Liebe und Glück! Schau nach vorn und nicht zurück!\n" +
                "AAA Tu was du willst, und steh dazu; denn dein Leben lebst nur du!", new SayingAuthor("unbekannt"), new SayingCategory("Lebenssprüche"), true, true));

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
                                }
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Log.i(TAG, "onDismissedBySwipeLeft: right swipe on " + position);
                                }
                            }
                        });

        recyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    public void createUserSayingDialog() {

        final View layout = LayoutInflater.from(context).inflate(R.layout.user_saying_dialog, null);
        TextInputLayout textInputLayoutCategory = (TextInputLayout) layout.findViewById(R.id.til_new_category);

        Spinner spinner = null;
        initDialogSpinner(layout, spinner, textInputLayoutCategory);


        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Neuer Spruch");
        dialog.setView(layout);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {



            }
        }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).create();
        dialog.show();
    }

    private void initDialogSpinner(View layout, Spinner spinner, final TextInputLayout tip) {

        final List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("HallO");
        spinnerArray.add("Test ");
        spinnerArray.add("Baum");
        spinnerArray.add("Auto");

        spinnerArray.add("Neue Kategorie hinzufügen");


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);


        spinner = (Spinner) layout.findViewById(R.id.spinner_dialog);
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
                } else {
                    tip.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}


