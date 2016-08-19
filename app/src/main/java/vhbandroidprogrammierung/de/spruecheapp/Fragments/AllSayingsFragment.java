package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import vhbandroidprogrammierung.de.spruecheapp.Activities.MainActivity;
import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerViewCreatorCustom;
import vhbandroidprogrammierung.de.spruecheapp.SayingDataObjects.Saying;

/**
 * Fragment für die Liste ALLER Sprüche
 */
public class AllSayingsFragment extends Fragment  {

    private static final String TAG = "AllSayingsFragment";
    private RecyclerView recyclerView;
    private List<Saying> sayingArrayList;
    private RecyclerAdapter recyclerAdapter;
    private Context context;
    private View view;
    private MaterialRefreshLayout materialRefreshLayout;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_all_sayings, null);
        this.activity = (MainActivity) getActivity();
        context = getContext();

        initRecyclerView();

        /*
        Entscheidet, ob das RefreshLayout vorhanden sein soll, oder nicht.
         */
        if (Config.allowRefreshLayout) {
            initRefreshLayout();
        }

        return view;
    }

    /**
     * Wenn die Liste ganz oben ist und man sie nach unten zieht, erscheint das RefreshLayout um die Datenbank von ihrer Quelle (Internet)zu updaten
     */
    private void initRefreshLayout() {
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                Log.i(TAG, "onRefresh: ");

                //TODO Sprüche neu aus dem Internet laden

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();

                    }
                }, 3000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Log.i(TAG, "onRefreshLoadMore: ");
            }

            @Override
            public void onfinish() {
                Log.i(TAG, "onfinish: ");
            }
        });

        // Refresh fertig
        materialRefreshLayout.finishRefresh();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_all_sayings);
        //sayingArrayList = new ArrayList<>();
        sayingArrayList = activity.getSayingList();
        Log.i(TAG, "initRecyclerView: " + sayingArrayList.size());

        RecyclerViewCreatorCustom recyclerViewCreatorCustom = new RecyclerViewCreatorCustom();
        recyclerAdapter = recyclerViewCreatorCustom.buildRecyclerViewWithAdapter(recyclerView, sayingArrayList, context);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void dataHasBeenChanged() {
        Log.i(TAG, "dataHasBeenChanged: ");
    }
}