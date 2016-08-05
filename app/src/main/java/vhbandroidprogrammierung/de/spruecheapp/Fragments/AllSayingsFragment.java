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

import java.util.ArrayList;

import vhbandroidprogrammierung.de.spruecheapp.Config;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewCreator;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.Saying;


public class AllSayingsFragment extends Fragment {

    private static final String TAG = "AllSayingsFragment";
    private RecyclerView recyclerView;
    private ArrayList<Saying> sayingArrayList;
    private RecyclerAdapter recyclerAdapter;
    private Context context;
    private View view;
    private MaterialRefreshLayout materialRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_all_sayings, null);
        context = getContext();

        initRecyclerView();

        if (Config.allowRefreshLayout) {
            initRefreshLayout();
        }

        return view;
    }

    /**
     * Wenn die Liste ganz oben ist un man sie nach unten zieht, erscheint das Refresh Layout um die Datenbank von ihrer Quelle (Internet)zu updaten
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
        sayingArrayList = new ArrayList<>();

        buildDemoSayings();

        recyclerAdapter = RecyclerViewCreator.buildRecyclerViewWithAdapter(recyclerView, sayingArrayList, context);
        recyclerView.setAdapter(recyclerAdapter);
    }

    // TODO Nur für Demo-Zwecke
    private void buildDemoSayings() {
        sayingArrayList.add(new Saying("Glaube an Wunder, Liebe und Glück! Schau nach vorn und nicht zurück!\n" +
                "Tu was du willst, und steh dazu; denn dein Leben lebst nur du!", "unbekannt", "Lebenssprüche ", true, false));
        sayingArrayList.add(new Saying("\"Lohnt es sich denn?\" fragt der Kopf.\n" +
                "\"Nein, aber es tut so gut!\" antwortet das Herz.", "unbekannt", "Lebenssprüche", true, false));
        sayingArrayList.add(new Saying("Ein langer Streit beweist, dass beide Seiten Unrecht haben.", "Voltaire", "Charakter", true, true));
        sayingArrayList.add(new Saying("Mütter lieben ihre Kinder mehr, als Väter es tun, weil sie sicher sein können, dass es ihre sind.", "Aristoteles", "Taufe ", false, false));
        sayingArrayList.add(new Saying("Ich wünschte ich könnte, aber ich will nicht!", "unbekannt", "Filmzitat ", false, false));
    }
}