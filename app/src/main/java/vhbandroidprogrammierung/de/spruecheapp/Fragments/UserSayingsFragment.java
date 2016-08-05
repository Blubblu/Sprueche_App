package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewCreator;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.Saying;

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
                "AAA Tu was du willst, und steh dazu; denn dein Leben lebst nur du!", "unbekannt", "Lebenssprüche ", true, true));
        userSayingArrayList.add(new Saying("\"BBB Lohnt es sich denn?\" fragt der Kopf.\n" +
                "\"BBB Nein, aber es tut so gut!\" antwortet das Herz.", "unbekannt", "Lebenssprüche", true, true));
        userSayingArrayList.add(new Saying("CCC Ein langer Streit beweist, dass beide Seiten Unrecht haben.", "Voltaire", "Charakter", true, true));
        userSayingArrayList.add(new Saying("DDD Mütter lieben ihre Kinder mehr, als Väter es tun, weil sie sicher sein können, dass es ihre sind.", "Aristoteles", "Taufe ", false, true));
        userSayingArrayList.add(new Saying("EEE Ich wünschte ich könnte, aber ich will nicht!", "unbekannt", "Filmzitat ", false, true));
    }

    /**
     * Elmente im RecyclerView können nach links oder rechts weggewischt werden
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


}