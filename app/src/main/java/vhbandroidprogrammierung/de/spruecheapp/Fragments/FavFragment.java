package vhbandroidprogrammierung.de.spruecheapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewCreator;
import vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff.RecyclerAdapter;
import vhbandroidprogrammierung.de.spruecheapp.Saying;

public class FavFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Saying> favSayingArrayList;
    private RecyclerAdapter recyclerAdapter;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fav_sayings, null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.fav_sayings);
        context = getContext();

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fav_sayings);
        favSayingArrayList = new ArrayList<>();

        buildDemoSayings();

        recyclerAdapter = RecyclerViewCreator.buildRecyclerViewWithAdapter(recyclerView, favSayingArrayList, context);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void buildDemoSayings() {
        favSayingArrayList.add(new Saying("AAA Glaube an Wunder, Liebe und Glück! Schau nach vorn und nicht zurück!\n" +
                "AAA Tu was du willst, und steh dazu; denn dein Leben lebst nur du!", "unbekannt", "Lebenssprüche ", true, false));
        favSayingArrayList.add(new Saying("\"BBB Lohnt es sich denn?\" fragt der Kopf.\n" +
                "\"BBB Nein, aber es tut so gut!\" antwortet das Herz.", "unbekannt", "Lebenssprüche", true, false));
        favSayingArrayList.add(new Saying("CCC Ein langer Streit beweist, dass beide Seiten Unrecht haben.", "Voltaire", "Charakter", true, true));
        favSayingArrayList.add(new Saying("DDD Mütter lieben ihre Kinder mehr, als Väter es tun, weil sie sicher sein können, dass es ihre sind.", "Aristoteles", "Taufe ", false, false));
        favSayingArrayList.add(new Saying("EEE Ich wünschte ich könnte, aber ich will nicht!", "unbekannt", "Filmzitat ", false, false));
    }
}